package com.irentaspro.bi.domain.port;

import com.irentaspro.bi.domain.model.*;
import java.util.*;

public interface ReporteRepository {
    List<KPIContrato> obtenerKPIContratos();

    KPIGlobal obtenerKPIGeneral();
}