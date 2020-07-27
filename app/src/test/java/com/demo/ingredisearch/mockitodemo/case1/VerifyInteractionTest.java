package com.demo.ingredisearch.mockitodemo.case1;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class VerifyInteractionTest {

    @Test
    public void testMethod() {
        // Arrange (Given)
        @SuppressWarnings("unchecked")
        List<String> mockedList = Mockito.mock(List.class);

        // Act (When)
        mockedList.add("first-element");
        mockedList.add("second-element");
        mockedList.add("third-element");
        mockedList.add("third-element");
        mockedList.clear();

        // Assert (Then)
        verify(mockedList).add("first-element");
        verify(mockedList).add("second-element");
        verify(mockedList, times(2)).add("third-element");
        verify(mockedList).clear();
    }
}
