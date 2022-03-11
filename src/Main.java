import java.io.File;

public class Main {

    public static void main(String[] args) {


        // I stedet for at oprette to filer, hhv. ddl og dml, har jeg valgt at lægge dem sammen til en fil.
        // Dette for mig virker mere simpelt, i det både ddl og dml filerne begge skal bruges for at oprette en database.

        File movies = new File("resources/imdb_data.csv");
        GenerateFile.createSQLFile(movies, "create_imdb_database");

    }

}
