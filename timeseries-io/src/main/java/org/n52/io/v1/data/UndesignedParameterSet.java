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
package org.n52.io.v1.data;

import org.joda.time.Interval;
import org.n52.io.IntervalWithTimeZone;
import org.n52.io.IoParameters;
import org.n52.io.Utils;



public class UndesignedParameterSet extends ParameterSet {

    private String[] timeseries;

    private String resultTime;

    private String format;

    // XXX refactor ParameterSet, DesignedParameterSet, UndesingedParameterSet and QueryMap

    /**
     * @return the timeseries ids
     */
    @Override
    public String[] getTimeseries() {
        return Utils.copy(timeseries);
    }

    /**
     * @param timeseries The timeseriesIds of interest.
     */
    void setTimeseries(String[] timeseries) {
        this.timeseries = Utils.copy(timeseries);
    }

    /**
     * @return the result time.
     */
    public String getResultTime() {
        return resultTime;
    }

    /**
     * @param resultTime Optional parameter, to define a result time in the request.
     */
    public void setResultTime(String resultTime) {
        this.resultTime = resultTime;
    }

    /**
     * @return the output format the raw data shall have.
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format Which output format the raw data shall have.
     */
    public void setFormat(String format) {
        this.format = format;
    }

    public static UndesignedParameterSet createForSingleTimeseries(String timeseriesId, IoParameters parameters) {
        UndesignedParameterSet parameterSet = parameters.toUndesignedParameterSet();
        parameterSet.setTimeseries(new String[] { timeseriesId });
        IntervalWithTimeZone timespan = parameters.getTimespan();
        parameterSet.setTimespan(timespan.toString());
        return parameterSet;
    }

    public static UndesignedParameterSet createFromDesignedParameters(DesignedParameterSet designedSet) {
        UndesignedParameterSet parameters = new UndesignedParameterSet();
        parameters.setTimeseries(designedSet.getTimeseries());
        parameters.setTimespan(designedSet.getTimespan());
        return parameters;
    }
}
