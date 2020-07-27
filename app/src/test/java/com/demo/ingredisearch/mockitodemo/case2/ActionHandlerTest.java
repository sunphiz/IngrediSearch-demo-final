package com.demo.ingredisearch.mockitodemo.case2;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class ActionHandlerTest {

    // SUT
    ActionHandler actionHandler;

    @Mock
    Service service;

    @Captor
    ArgumentCaptor<Callback> mCallbackCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        actionHandler = new ActionHandler(service);
    }

    @Test
    public void testMethod1() {
        // Arrange (Given)
        doAnswer(answer -> {
            Callback callback = answer.getArgument(1);
            callback.reply(new Response("Success"));
            return null;
        }).when(service).getResponse(anyString(), isA(Callback.class));

        // Act (When)
        actionHandler.doRequest();

        // Assert (Then)
        assertThat(actionHandler.getValue(), is("Success"));
    }


    @Test
    public void testMethod2() {
        // Arrange (Given)

        // Act (When)
        actionHandler.doRequest();

        // Assert (Then)
        verify(service).getResponse(anyString(), mCallbackCaptor.capture());
        mCallbackCaptor.getValue().reply(new Response("Success"));
        assertThat(actionHandler.getValue(), is("Success"));
    }

    @Test
    public void testMethod3() {
        // Arrange (Given)
        doNothing().when(service).getResponse(anyString(), mCallbackCaptor.capture());

        // Act (When)
        actionHandler.doRequest();

        // Assert (Then)
        mCallbackCaptor.getValue().reply(new Response("Success"));
        assertThat(actionHandler.getValue(), is("Success"));
    }

}