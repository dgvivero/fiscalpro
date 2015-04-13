package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by German on 31/03/2015.
 */
@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id_seccion")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.VERSION_NUMBER,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.Seccion"),
        @javax.jdo.annotations.Query(
                name = "findByNumber", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.Seccion "
                        + "WHERE nroSeccion.startsWith(:nroSeccion)")
})
@javax.jdo.annotations.Unique(name="SECCION_UNQ", members = {"nroSeccion"})
@DomainObject(objectType = "SECCION")

public class Seccion implements Comparable<Seccion> {

    //region > nroSeccion (property)
    private Integer nroSeccion;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "False")
    public Integer getNroSeccion() {
        return nroSeccion;
    }

    public void setNroSeccion(final Integer nroSeccion) {
        this.nroSeccion = nroSeccion;
    }
    //endregion

    //region > descripcion (property)
    private String descripcion;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "True")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }
    //endregion

    //region > Distrito (property)
    private Distrito distrito;

    @MemberOrder(sequence = "3")
    @Column(allowsNull = "False")
    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(final Distrito distrito) {
        this.distrito = distrito;
    }

    public Distrito defaultDistrito() {
        return distritos.listByDNI("Capital Federal").get(0);
    }
    //endregion

    //region > circuitos (collection)
    @Persistent(mappedBy = "seccion")
    @Column(allowsNull = "True")
    private SortedSet<Circuito> circuitos = new TreeSet<Circuito>();

    @MemberOrder(sequence = "1")
    public SortedSet<Circuito> getCircuitos() {
        return circuitos;
    }

    public void setCircuitos(final SortedSet<Circuito> circuitos) {
        this.circuitos = circuitos;
    }
    //endregion

    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        // TODO: append to org.apache.isis.applib.util.TitleBuffer, typically value properties
        buf.append("Seccion : ");
        buf.concat(nroSeccion);
        buf.concat(" - ");
        buf.concat(descripcion);
        return buf.toString();

    }

    //region compareTo
    @Override
    public int compareTo(final Seccion other) {
        return ObjectContracts.compare(this, other, "nroSeccion");
    }

    //endregion

    //region  >  (injected)
    @javax.inject.Inject
    private DomainObjectContainer contenedor;

    @javax.inject.Inject
    private Distritos distritos;
    //endregion


}
