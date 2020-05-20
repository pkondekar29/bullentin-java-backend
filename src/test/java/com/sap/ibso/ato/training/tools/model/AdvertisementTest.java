package com.sap.ibso.ato.training.tools.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class AdvertisementTest {

	@Test
	public void equalsShouldReturnTrueForTheSameObjects() {
		Advertisement left = new Advertisement(3L, "Hello World");
		assertTrue(left.equals(left));
	}

	@Test
	public void equalsShouldReturnTrueForEqualObjects() {
		Advertisement left = new Advertisement(3L, "Hello World");
		Advertisement right = new Advertisement(3L, "Hello World");
		assertTrue(left.equals(right));
	}

	@Test
	public void equalsShouldReturnFalseForNonEqualObjects() {
		Advertisement left = new Advertisement(3L, "Hello World");
		Advertisement right = new Advertisement(4L, "Hello You");
		assertFalse(left.equals(right));
	}

	@Test
	public void equalsShouldReturnFalseForNull() {
		Advertisement left = new Advertisement(3L, "Hello World");
		assertFalse(left.equals(null));
	}

	@Test
	public void hashCodeShouldReturnTheSameHashCodeForEqualObjects() {
		Advertisement left = new Advertisement(3L, "Hello World");
		Advertisement right = new Advertisement(3L, "Hello World");
		assertEquals(left.hashCode(), right.hashCode());
	}

	@Test
	public void toStringShouldReturnAStringThatContainsIdAndTitle() {
		Advertisement ad = new Advertisement(373L, "Hello World");
		String toString = ad.toString();
		assertTrue(toString.indexOf("373") > -1);
		assertTrue(toString.indexOf("Hello World") > -1);
	}
}
