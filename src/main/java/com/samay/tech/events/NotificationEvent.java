package com.samay.tech.events;

import com.samay.tech.model.Notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Bhaji Shaik
 */
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class NotificationEvent {

	private Notification notification;

}
