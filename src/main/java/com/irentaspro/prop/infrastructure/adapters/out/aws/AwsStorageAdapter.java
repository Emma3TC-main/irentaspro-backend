package com.irentaspro.prop.infrastructure.adapters.out.aws;

import com.irentaspro.prop.domain.model.DocumentoPropiedad;
import com.irentaspro.prop.domain.services.StorageService;

public class AwsStorageAdapter extends StorageService {
    @Override
    public String upload(DocumentoPropiedad documento) {
        // Aqui se implementaría integración real con AWS S3 o similar
        return super.upload(documento);
    }
}
