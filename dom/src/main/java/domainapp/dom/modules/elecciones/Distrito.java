package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.util.ObjectContracts;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by German on 24/03/2015.
 */

@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="nroDistrito")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.VERSION_NUMBER,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.Distrito "),
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.Distrito "
                        + "WHERE nombreDistrito.startsWith(:nombreDistrito)")
})
@javax.jdo.annotations.Unique(name="DISTRITO_UNQ", members = {"nroDistrito"})
@DomainObject(
        objectType = "DISTRITO"
)
public class Distrito implements Comparable<Distrito>{

    //region > nrodistrito (property)
    private int nroDistrito;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "False")
    public int getNroDistrito() {
        return nroDistrito;
    }

    public void setNroDistrito(final int nroDistrito) {
        this.nroDistrito = nroDistrito;
    }

    //endregion

//region > nombreDistrito (property)
    private String nombreDistrito;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false")
    @Title
    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(final String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }
//endregion
/*
    //region > Secciones (collection)
    @Persistent(mappedBy = "distrito")
    private SortedSet<Seccion> Secciones = new TreeSet<Seccion>();

    @MemberOrder(name = "Secciones", sequence = "3")
    public SortedSet<Seccion> getSecciones() {
        return Secciones;
    }

    public void setSecciones(final SortedSet<Seccion> Secciones) {
        this.Secciones = Secciones;
    }

    //region > addSeccion (action)
    @MemberOrder(name = "Secciones", sequence = "1")
    @ActionLayout(named = "agregar Seccion")
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    public Distrito addToSecciones(@ParameterLayout(named = "Nro de Seccion") final Integer nroSeccion) {
        final Seccion se = container.newTransientInstance(Seccion.class);
        se.setDistrito(this);
        se.setNroSeccion(nroSeccion);
        getSecciones().add(se);
        container.persistIfNotAlready(se);
        return this;
    }

    @MemberOrder(name = "Secciones", sequence = "2")
    @ActionLayout(named = "eliminar seccion", cssClass = "x-caution")
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    public Distrito removeFromSecciones(final Seccion seccion) {
       getSecciones().remove(seccion);
        container.informUser("Se ha eliminado la seccion" + seccion.title());
        return this;
    }
    public boolean hideRemoveFromSecciones() {
        return getSecciones().isEmpty();
    }

    //endregion


//region > remove (action)
    @ActionLayout(named = "eliminar Distrito")
    @Action(invokeOn=InvokeOn.OBJECT_AND_COLLECTION)
    public List<Distrito> remove() {
        container.removeIfNotAlready(this);
        List d =distritos.listAll();
        if(d.isEmpty()) {
            container.informUser("No hay Distritos cargados");
        }
        return d;

    }
    public boolean hideRemove() {
        return distritos.listAll().isEmpty();
    }
//endregion
*/
//region > compareTo
    @Override
    public int compareTo(final Distrito other) {
        return ObjectContracts.compare(this, other, "nrodistrito");
    }
//endregion

//region > injected services

    @javax.inject.Inject
    Distritos distritos;

    @javax.inject.Inject
    DomainObjectContainer container;

//endregion


}
