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
package org.n52.sensorweb.v1.spi;

import org.n52.io.IoParameters;
import org.n52.io.v1.data.TimeseriesMetadataOutput;

public class TransformingTimeseriesService extends TransformationService implements ParameterService<TimeseriesMetadataOutput> {

    private final ParameterService<TimeseriesMetadataOutput> composedService;

    public TransformingTimeseriesService(ParameterService<TimeseriesMetadataOutput> toCompose) {
        this.composedService = toCompose;
    }

    @Override
    public TimeseriesMetadataOutput[] getExpandedParameters(IoParameters query) {
        TimeseriesMetadataOutput[] metadata = composedService.getExpandedParameters(query);
        return transformStations(query, metadata);
    }

    @Override
    public TimeseriesMetadataOutput[] getCondensedParameters(IoParameters query) {
        TimeseriesMetadataOutput[] metadata = composedService.getCondensedParameters(query);
        return transformStations(query, metadata);
    }

    @Override
    public TimeseriesMetadataOutput[] getParameters(String[] items) {
        TimeseriesMetadataOutput[] metadata = composedService.getParameters(items);
        return transformStations(IoParameters.createDefaults(), metadata);
    }

    @Override
    public TimeseriesMetadataOutput[] getParameters(String[] items, IoParameters query) {
        TimeseriesMetadataOutput[] metadata = composedService.getParameters(items, query);
        return transformStations(query, metadata);
    }

    @Override
    public TimeseriesMetadataOutput getParameter(String item) {
        TimeseriesMetadataOutput metadata = composedService.getParameter(item, IoParameters.createDefaults());
        transformInline(metadata.getStation(), IoParameters.createDefaults());
        return metadata;
    }

    @Override
    public TimeseriesMetadataOutput getParameter(String timeseriesId, IoParameters query) {
        TimeseriesMetadataOutput metadata = composedService.getParameter(timeseriesId, query);
        if (metadata != null) {
            transformInline(metadata.getStation(), query);
        }
        return metadata;
    }

    private TimeseriesMetadataOutput[] transformStations(IoParameters query, TimeseriesMetadataOutput[] metadata) {
        for (TimeseriesMetadataOutput timeseriesMetadata : metadata) {
            transformInline(timeseriesMetadata.getStation(), query);
        }
        return metadata;
    }

}
