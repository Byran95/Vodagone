package oose.dea.dienst;

import oose.dea.domain.SettingsReader;

/**
 * Created by Anders Egberts on 18/10/2016.
 */
public class DienstDAOFactory {

    /**
     * Gets the Data Acces Object based on the databasetype in settings.xml. (Eis: IM1)
     * @return the appropriate DAO or null if no DAO matching the database type was found.
     */
    public static IDienstAccess getAccessObject() {
        switch ( SettingsReader.getPropertyString( SettingsReader.DATABASE_TYPE_PROPERTY , "MySQL" ) ) {
            case "MySQL":
                return new DienstDAOMySQL();
        }
        return null;
    }
}