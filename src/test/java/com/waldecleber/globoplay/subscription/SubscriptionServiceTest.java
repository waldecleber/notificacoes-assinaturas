package com.waldecleber.globoplay.subscription;

import com.waldecleber.globoplay.subscription.config.SubscriptionAMQPConfig;
import com.waldecleber.globoplay.subscription.dto.StatusDTO;
import com.waldecleber.globoplay.subscription.dto.SubscriptionDTO;
import com.waldecleber.globoplay.subscription.exceptions.SubscriptionWithoutStatusException;
import com.waldecleber.globoplay.subscription.model.Status;
import com.waldecleber.globoplay.subscription.model.Subscription;
import com.waldecleber.globoplay.subscription.repository.SubscriptionRepository;
import com.waldecleber.globoplay.subscription.service.SubscriptionService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.OffsetDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


public class SubscriptionServiceTest {

	private SubscriptionRepository repository;
	private ModelMapper mapper;
	private RabbitTemplate rabbitTemplate;
	private SubscriptionService service;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Before
	public void setUp() {
		this.repository = mock(SubscriptionRepository.class);
		this.mapper = mock(ModelMapper.class);
		this.rabbitTemplate = mock(RabbitTemplate.class);
		this.service = new SubscriptionService(repository, mapper, rabbitTemplate);
	}

	@Test
	public void save_subscription_purchased_sucess() {
		SubscriptionDTO subscriptionDTO = buildSubscriptionDTO();
		Subscription subscription = buildSubscriptionEntity();

		when(repository.save(subscription)).thenReturn(subscription);
		when(mapper.map(subscriptionDTO, Subscription.class)).thenReturn(subscription);

		SubscriptionDTO subscriptionSaved = service.save(subscriptionDTO);

		error.checkThat(subscriptionSaved.getCreatedAt().toLocalDate()
				.isEqual(OffsetDateTime.now().toLocalDate()),
				is(true));
	}

	@Test(expected = SubscriptionWithoutStatusException.class)
	public void dont_save_subscription_without_status_exception() {
		SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
				.id("5793cf6b3fd833521db8c420955e6f01")
				.build();
		Subscription subscription = Subscription.builder()
				.id("5793cf6b3fd833521db8c420955e6f01")
				.build();

		when(repository.save(subscription)).thenReturn(subscription);
		when(mapper.map(subscriptionDTO, Subscription.class)).thenReturn(subscription);

		SubscriptionDTO subscriptionSaved = service.save(subscriptionDTO);
	}

	private SubscriptionDTO buildSubscriptionDTO() {
		StatusDTO statusDTO = buildStatusDTO();
		return SubscriptionDTO.builder()
				.id("5793cf6b3fd833521db8c420955e6f01")
				.statusId(statusDTO)
				.build();
	}

	private StatusDTO buildStatusDTO() {
		return StatusDTO.builder()
				.name("SUBSCRIPTION_PURCHASED")
				.build();
	}

	private Subscription buildSubscriptionEntity() {
		Status status = buildStatusEntity();
		return Subscription.builder()
				.id("5793cf6b3fd833521db8c420955e6f01")
				.statusId(status)
				.build();
	}

	private Status buildStatusEntity() {
		return Status.builder()
				.name("SUBSCRIPTION_PURCHASED")
				.build();
	}

}
