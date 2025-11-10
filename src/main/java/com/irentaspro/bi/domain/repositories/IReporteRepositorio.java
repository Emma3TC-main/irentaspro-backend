package com.irentaspro.bi.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.irentaspro.bi.domain.model.Reporte;
import com.irentaspro.common.domain.model.Repositorio;

public interface IReporteRepositorio extends Repositorio<Reporte> {
    List<Reporte> buscarPorTipo(String tipo);

    List<Reporte> listarRecientes(int limite);

    @Override
    Reporte guardar(Reporte reporte);

    @Override
    Optional buscarPorId(UUID id);

    @Override
    void eliminar(UUID id);
}
