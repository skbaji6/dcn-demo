package com.samay.tech.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Notification implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5146205616976389352L;
	private String table;
    private String rowId;
    private String operation;
}
