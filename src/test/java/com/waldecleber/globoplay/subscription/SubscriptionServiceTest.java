package com.waldecleber.globoplay.subscription;

import com.waldecleber.globoplay.subscription.dto.StatusDTO;
import com.waldecleber.globoplay.subscription.dto.SubscriptionDTO;
import com.waldecleber.globoplay.subscription.exceptions.SubscriptionWithoutStatusException;
import com.waldecleber.globoplay.subscription.model.Status;
import com.waldecleber.globoplay.subscription.model.Subscription;
import com.waldecleber.globoplay.subscription.repository.StatusRepository;
import com.waldecleber.globoplay.subscription.repository.SubscriptionRepository;
import com.waldecleber.globoplay.subscription.service.SubscriptionService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.constant.IntegerConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;


public class SubscriptionServiceTest {

	public static final String SUBSCRIPTION_ID = "5793cf6b3fd833521db8c420955e6f01";
	public static final String SUBSCRIPTION_PURCHASED = "SUBSCRIPTION_PURCHASED";
	private SubscriptionRepository repository;
	private StatusRepository statusRepository;
	private ModelMapper mapper;
	private RabbitTemplate rabbitTemplate;
	private SubscriptionService service;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Before
	public void setUp() {
		this.repository = mock(SubscriptionRepository.class);
		this.statusRepository = mock(StatusRepository.class);
		this.mapper = mock(ModelMapper.class);
		this.rabbitTemplate = mock(RabbitTemplate.class);
		this.service = new SubscriptionService(repository, statusRepository, mapper, rabbitTemplate);
	}

	@Test
	public void save_subscription_purchased_sucess() {
		Status status = buildStatusEntity();
		SubscriptionDTO subscriptionDTO = buildSubscriptionDTO();
		Subscription subscription = buildSubscriptionEntity();

		when(statusRepository.findByName(subscription.getStatus().getName())).thenReturn(Optional.of(status));
		when(repository.save(subscription)).thenReturn(subscription);
		when(mapper.map(subscriptionDTO, Subscription.class)).thenReturn(subscription);

		subscription.onPrePersist();
		service.save(subscriptionDTO);

		error.checkThat(subscription.getCreatedAt().toLocalDate().isEqual(LocalDate.now()),
				is(true));
	}

	@Test(expected = SubscriptionWithoutStatusException.class)
	public void dont_save_subscription_without_status_exception() {
		SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
				.id(SUBSCRIPTION_ID)
				.build();
		Subscription subscription = Subscription.builder()
				.id(SUBSCRIPTION_ID)
				.build();

		when(repository.save(subscription)).thenReturn(subscription);
		when(mapper.map(subscriptionDTO, Subscription.class)).thenReturn(subscription);

		service.save(subscriptionDTO);
	}

	private SubscriptionDTO buildSubscriptionDTO() {
		StatusDTO statusDTO = buildStatusDTO();
		return SubscriptionDTO.builder()
				.id(SUBSCRIPTION_ID)
				.statusId(statusDTO)
				.build();
	}

	private StatusDTO buildStatusDTO() {
		return StatusDTO.builder()
				.name(SUBSCRIPTION_PURCHASED)
				.build();
	}

	private Subscription buildSubscriptionEntity() {
		Status status = buildStatusEntity();
		return Subscription.builder()
				.id(SUBSCRIPTION_ID)
				.statusId(status)
				.build();
	}

	private Status buildStatusEntity() {
		return Status.builder()
				.name(SUBSCRIPTION_PURCHASED)
				.build();
	}

}
