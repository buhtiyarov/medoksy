package mapper;

import java.sql.Connection;

public class MapperFactory {
    private Connection dbh;
    private ClientMapper clientMapper;
    private VisitMapper visitMapper;
    private DiagnosisMapper diagnosisMapper;
    private ImageMapper imageMapper;

    public MapperFactory(Connection dbh) {
        this.dbh = dbh;
    }

    public ClientMapper getClientMapper() {
        if (clientMapper == null) {
            clientMapper = new ClientMapper();
            clientMapper.setConnection(dbh);
            clientMapper.setMapperFactory(this);
        }
        return clientMapper;
    }

    public VisitMapper getVisitMapper() {
        if (visitMapper == null) {
            visitMapper = new VisitMapper();
            visitMapper.setConnection(dbh);
            visitMapper.setMapperFactory(this);
        }
        return visitMapper;
    }

    public DiagnosisMapper getDiagnosisMapper() {
        if (diagnosisMapper == null) {
            diagnosisMapper = new DiagnosisMapper();
            diagnosisMapper.setConnection(dbh);
            diagnosisMapper.setMapperFactory(this);
        }
        return diagnosisMapper;
    }

    public ImageMapper getImageMapper() {
        if (imageMapper == null) {
            imageMapper = new ImageMapper();
            imageMapper.setConnection(dbh);
            imageMapper.setMapperFactory(this);
        }
        return imageMapper;
    }
}
