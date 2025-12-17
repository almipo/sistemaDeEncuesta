package service.encuestaService;


import data.Model.Pregunta;
import service.IOpcionRespuestaService;
import service.IPreguntaCompuestaService;
import service.IPreguntaService;
import service.ServiceException;

import java.util.List;


public class PreguntaCompuestaService implements IPreguntaCompuestaService {

    private final IPreguntaService preguntaService;
    private final IOpcionRespuestaService opcionService;



    public PreguntaCompuestaService(IPreguntaService preguntaService,
                                    IOpcionRespuestaService opcionService) {
        this.preguntaService = preguntaService;
        this.opcionService   = opcionService;
    }



    @Override
public Pregunta crearConOpcionesPorDefecto(int encuestaId, String texto) throws ServiceException {

        Pregunta pregunta = preguntaService.crearPregunta(encuestaId, texto);


        String[] labels = {"Muy malo", "Malo", "Regular", "Bueno", "Muy bueno"};
        for (int i = 0; i < labels.length; i++) {

            opcionService.crearOpcion(pregunta.getIdPregunta(), i + 1, labels[i]);
        }

        return pregunta;
    }


    @Override
    public Pregunta crearConOpcionesVariables(int encuestaId,
                                              String texto,
                                              List<String> etiquetas,
                                              boolean permitirLibre) throws ServiceException {

        Pregunta pregunta = preguntaService.crearPregunta(encuestaId, texto);


        for (int i = 0; i < etiquetas.size(); i++) {
            int valor = i + 1;
            String label = etiquetas.get(i);
            opcionService.crearOpcion(pregunta.getIdPregunta(), valor, label);
        }


        return pregunta;
    }
}
