package edu.neu.cs4500.services;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.cs4500.models.FrequentlyAskedQuestion;

@RunWith(SpringRunner.class)
@WebMvcTest(FrequentlyAskedQuestion.class)
class FrequentlyAskedQuestionServiceTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private FrequentlyAskedQuestionService service;

	@Test
	public void getFAQShouldReturnFAQFromService()
			throws Exception {
		FrequentlyAskedQuestion q1 = new FrequentlyAskedQuestion(123, "first question", "How old are you?");
		FrequentlyAskedQuestion q2 = new FrequentlyAskedQuestion(234, "second question", "How many employees do you have?");

		ArrayList<FrequentlyAskedQuestion> faqs = new ArrayList<>(); 
		faqs.add(q1);
		faqs.add(q2); 
		when(service.findAllFaqs()).thenReturn(faqs);
		this.mockMvc
		.perform(get("/api/faqs"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.frequentlyAskedQuestions",
				hasSize(2)))
		.andExpect(jsonPath("$.frequentlyAskedQuestions[*].title",
				containsInAnyOrder("first question", "second question")));  
	}

	@Test
	public void getFAQByIdShouldReturnFAQFromService()
			throws Exception {
		FrequentlyAskedQuestion q1 = new FrequentlyAskedQuestion(123, "first question", "How old are you?");
		FrequentlyAskedQuestion q2 = new FrequentlyAskedQuestion(234, "second question", "How many employees do you have?");

		ArrayList<FrequentlyAskedQuestion> faqs = new ArrayList<>(); 
		faqs.add(q1);
		faqs.add(q2); 
		when(service.findAllFaqs()).thenReturn(faqs);
		this.mockMvc
		.perform(get("/api/faqs/123"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.title",
				is("first question")));  
	}


	@Test
	public void getFAQShouldReturnEmptyFAQFromService()
			throws Exception {
		ArrayList<FrequentlyAskedQuestion> faqs = new ArrayList<>(); 
		when(service.findAllFaqs()).thenReturn(faqs);
		this.mockMvc
		.perform(get("/api/faqs"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.frequentlyAskedQuestions",
				hasSize(0)));  
	}

	@Test
	public void deleteFAQShouldNotReturnFAQFromService()
			throws Exception {
		FrequentlyAskedQuestion q1 = new FrequentlyAskedQuestion(123, "first question", "How old are you?");
		FrequentlyAskedQuestion q2 = new FrequentlyAskedQuestion(234, "second question", "How many employees do you have?");

		ArrayList<FrequentlyAskedQuestion> faqs = new ArrayList<>(); 
		faqs.add(q1);
		faqs.add(q2); 
		when(service.findAllFaqs()).thenReturn(faqs);
		this.mockMvc
		.perform(delete("/api/faqs/123"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.frequentlyAskedQuestions",
				hasSize(1)))
		.andExpect(jsonPath("$.id", is(234)))
		.andExpect(jsonPath("$.frequentlyAskedQuestions[*].title",
				containsInAnyOrder("second question")));  
	}

	@Test
	public void updateFAQShouldReturnEditedFAQFromService()
			throws Exception {
		FrequentlyAskedQuestion q1 = new FrequentlyAskedQuestion(123, "first question", "How old are you?");
		FrequentlyAskedQuestion q2 = new FrequentlyAskedQuestion(234, "second question", "How many employees do you have?");

		FrequentlyAskedQuestion editq1 = new FrequentlyAskedQuestion(123, "first question", "What is your age");

		ArrayList<FrequentlyAskedQuestion> faqs = new ArrayList<>(); 
		faqs.add(q1);
		faqs.add(q2); 
		when(service.findAllFaqs()).thenReturn(faqs);
		((ResultActions) ((MockHttpServletRequestBuilder) this.mockMvc
				.perform(delete("/api/faqs/123")))
				.content(asJsonString(editq1)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.frequentlyAskedQuestions",
				hasSize(2)))
		.andExpect(jsonPath("$.frequentlyAskedQuestions[*].question",
				containsInAnyOrder("How many employees do you have?", "What is your age")));  
	}

	@Test
	public void createFAQShouldReturnCreatedFAQFromService()
			throws Exception {
		FrequentlyAskedQuestion q1 = new FrequentlyAskedQuestion(123, "first question", "How old are you?");
		FrequentlyAskedQuestion q2 = new FrequentlyAskedQuestion(234, "second question", "How many employees do you have?");

		FrequentlyAskedQuestion newQ = new FrequentlyAskedQuestion(345, "third question", "Do you like your job?");

		ArrayList<FrequentlyAskedQuestion> faqs = new ArrayList<>(); 
		faqs.add(q1);
		faqs.add(q2); 
		when(service.findAllFaqs()).thenReturn(faqs);
		((ResultActions) ((MockHttpServletRequestBuilder) this.mockMvc
				.perform(post("/api/faqs")))
				.content(asJsonString(newQ)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.frequentlyAskedQuestions",
				hasSize(3)))
		.andExpect(jsonPath("$.frequentlyAskedQuestions[*].question",
				containsInAnyOrder("How many employees do you have?", "How old are you?", "Do you like your job?")));  
	}

	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}


