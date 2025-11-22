package com.irentaspro.prop.infrastructure.adapters.in.rest;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.irentaspro.prop.application.dto.DocumentoRequest;
import com.irentaspro.prop.application.services.DescargarDocumentoService;
import com.irentaspro.prop.application.services.ListarDocumentosService;
import com.irentaspro.prop.application.services.SubirDocumentoPropiedadService;

@RestController
@RequestMapping("/api/v1/propiedades/{propId}/documentos")
@CrossOrigin(origins = "*") // en producción cambia "*" a tu dominio
public class DocumentoPropiedadController {

    private static final Logger log = LoggerFactory.getLogger(DocumentoPropiedadController.class);

    private final SubirDocumentoPropiedadService subirService;
    private final ListarDocumentosService listarService;
    private final DescargarDocumentoService descargarService;

    public DocumentoPropiedadController(
            SubirDocumentoPropiedadService subirService,
            ListarDocumentosService listarService,
            DescargarDocumentoService descargarService) {
        this.subirService = subirService;
        this.listarService = listarService;
        this.descargarService = descargarService;
    }

    // ============================================================
    // SUBIR DOCUMENTO
    // ============================================================
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> subirDocumento(
            @PathVariable UUID propId,
            @RequestPart("metadata") DocumentoRequest request,
            @RequestPart("file") MultipartFile file) throws Exception {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "El archivo está vacío o no fue enviado."));
        }

        log.info("Subiendo archivo '{}' ({} bytes) para propiedad {}",
                file.getOriginalFilename(), file.getSize(), propId);

        String id = subirService.ejecutar(propId, request, file);

        log.info("Documento subido con ID {}", id);

        return ResponseEntity.ok(Map.of("documentoId", id));
    }

    // ============================================================
    // LISTAR DOCUMENTOS DE UNA PROPIEDAD
    // ============================================================
    @GetMapping
    public ResponseEntity<?> listar(@PathVariable UUID propId) {
        log.info("Listando documentos para propiedad {}", propId);
        return ResponseEntity.ok(listarService.ejecutar(propId));
    }

    // ============================================================
    // DESCARGAR ARCHIVO
    // ============================================================
    @GetMapping("/{docId}/archivo")
    public ResponseEntity<?> descargarArchivo(@PathVariable UUID docId) throws Exception {

        log.info("Descargando archivo {}", docId);

        GridFsResource data = descargarService.ejecutar(docId);

        if (!data.exists()) {
            log.warn("Archivo {} no existe en GridFS", docId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Archivo no encontrado en GridFS"));
        }

        String contentType = (data.getContentType() != null)
                ? data.getContentType()
                : MediaType.APPLICATION_OCTET_STREAM_VALUE;

        String filename = data.getFilename() != null ? data.getFilename() : (docId + ".bin");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        log.info("Enviando archivo '{}' con contentType={}", filename, contentType);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(data.getInputStream()));
    }

    // ============================================================
    // MANEJO GLOBAL DE ERRORES DEL CONTROLADOR
    // ============================================================
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        log.error("Error de validación: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("Error interno del servidor", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Ocurrió un error inesperado."));
    }
}
