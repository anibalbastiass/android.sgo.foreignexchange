package com.anibalbastias.android.foreignexchange.presentation.util;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public abstract class RobolectricBaseTest {
  @Rule public final MockitoRule mockitoRule = MockitoJUnit.rule();
}
