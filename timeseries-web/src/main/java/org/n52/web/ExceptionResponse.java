/**
 * Copyright (C) 2013-2015 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 as publishedby the Free
 * Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of the
 * following licenses, the combination of the program with the linked library is
 * not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed under
 * the aforementioned licenses, is permitted by the copyright holders if the
 * distribution is compliant with both the GNU General Public License version 2
 * and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package org.n52.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Wraps all {@link WebException}s thrown by controlled workflow. If unexpected Exceptions occur a general
 * {@link InternalServerException} should be wrapped so that all exceptions interrupting the expected workflow
 * can be serialized and returned to the requesting user/service. <br/>
 * <br/>
 * To ensure all exceptions are handled and communicated to the user all Web bindings shall inherit from
 * {@link BaseController} which is configured by default to serve as a central {@link ExceptionHandler}.
 */
public final class ExceptionResponse {

    // TODO add documentation url for details

    // TODO make stack tracing configurable

    private Throwable exception;

    private HttpStatus statusCode;

    private String[] hints;

    public static ExceptionResponse createExceptionResponse(WebException e, HttpStatus statusCode) {
        return new ExceptionResponse(e.getThrowable(), statusCode, e.getHints());
    }

    private ExceptionResponse(Throwable e, HttpStatus statusCode) {
        this(e, statusCode, null);
    }

    private ExceptionResponse(Throwable e, HttpStatus statusCode, String[] hints) {
        this.hints = hints != null ? hints.clone() : null;
        this.statusCode = statusCode;
        this.exception = e;
    }

    public int getStatusCode() {
        return statusCode.value();
    }

    public String getReason() {
        return statusCode.getReasonPhrase();
    }

    public String getUserMessage() {
        return exception.getMessage();
    }

    public String getDeveloperMessage() {
        Throwable causedBy = exception.getCause();
        return causedBy != null ? formatMessageOutput(causedBy) : null;
    }

    private String formatMessageOutput(Throwable causedBy) {
        return causedBy.getMessage().replace("\"", "'");
    }

    public String[] getHints() {
        return hints;
    }

}
