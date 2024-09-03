package com.example.demo.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@Slf4j
public class HttpLogger extends OncePerRequestFilter {


	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response,
	                                 FilterChain filterChain) throws ServletException, IOException {

		ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

		long start = System.currentTimeMillis();

		try {
			filterChain.doFilter(wrappedRequest, wrappedResponse);
		} finally {
			long elapsed = System.currentTimeMillis() - start;
			String requestBody = new String(wrappedRequest.getContentAsByteArray());
			String responseBody = new String(wrappedResponse.getContentAsByteArray());

			log.info("""
     
					[{}] {} - {} ms
					RequestBody : {}
					ResponseBody : {}
					""",
					wrappedRequest.getMethod(),
					wrappedRequest.getRequestURI(),
					elapsed,
					requestBody,
					responseBody);
			wrappedResponse.copyBodyToResponse();
		}
	}


//	@Override
//	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response,
//	                                 FilterChain filterChain) throws ServletException, IOException {
//		String uri = request.getRequestURI();
//		String method = request.getMethod();
//		long start = System.currentTimeMillis();
//		long elapsed = System.currentTimeMillis() - start;
//
//		ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
//		ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
////		String requestBody = wrappedRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
////		String responseBody = Arrays.toString(wrappedResponse.getContentAsByteArray());
//
//		try {
//			filterChain.doFilter(request, response);
//		} finally {
//
//
//			log.info("[{}] {} - {}ms", method, uri, elapsed);
//			log.info("request body -> {}", wrappedRequest.getReader());
////			log.info("response body -> {}", wrappedResponse());
//		}
//	}


//	@Override
//	protected void doFilterInternal (
//			HttpServletRequest request, HttpServletResponse response,
//			FilterChain filterChain
//	) throws ServletException, IOException {
//		try {
//			filterChain.doFilter(request, response);
//		} finally {
//
//		}
//	}


}