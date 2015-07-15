package domainapp.dom.modules.elecciones;

import com.google.common.collect.Lists;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MinLength;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import java.util.Collection;

/**
 * Created by German on 14/07/2015.
 */
@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id_lista_interna")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.VERSION_NUMBER,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.ListaInterna "),
        @javax.jdo.annotations.Query(
                name = "findByNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.ListaInterna "
                        + "WHERE nombre.startsWith(:nombre)")
})
@javax.jdo.annotations.Unique(name="CANDIDATO_UNQ", members = {"nombre"})
@DomainObject( objectType = "ListaInterna")

public class ListaInterna {
    //region > nombre (property)
    private String nombre;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "False")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }
    //endregion

    //region > Partido (property)
    private PartidoPolitico partido;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "False")
    public PartidoPolitico getPartido() {
        return partido;
    }

    public void setPartido(final PartidoPolitico Partido) {
        this.partido = Partido;
    }


 //endregion


 //region > injected services

    @javax.inject.Inject
    PartidosPoliticos partidos;

}
