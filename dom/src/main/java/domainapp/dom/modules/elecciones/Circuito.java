package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.util.ObjectContracts;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by German on 31/03/2015.
 */
@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id_circuito")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.VERSION_NUMBER,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.Circuito "),
        @javax.jdo.annotations.Query(
                name = "findByDocument", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.Circuito "
                        + "WHERE nroCircuito =:nroCircuito")
})
@javax.jdo.annotations.Unique(name="CIRCUITO_UNQ", members = {"distrito, seccion, nroCircuito"})
@DomainObject(
        objectType = "CIRCUITO"
)
public class Circuito implements  Comparable<Circuito>{

    //region > distrito (property)
    private Distrito distrito;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "False")
    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(final Distrito distrito) {
        this.distrito = distrito;
    }
    //endregion

    //region > seccion (property)
    private Seccion seccion;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "False")
    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(final Seccion seccion) {
        this.seccion = seccion;
    }
    //endregion


    //region > nroCircuito (property)
    private Integer nroCircuito;

    @MemberOrder(sequence = "3")
    @Column(allowsNull = "False")
    public Integer getNroCircuito() {
        return nroCircuito;
    }

    public void setNroCircuito(final Integer nroCircuito) {
        this.nroCircuito = nroCircuito;
    }
    //endregion



/*
    //region > mesas (collection)
    @Persistent(mappedBy = "circuito")
    private SortedSet<MesaElectoral> mesas = new TreeSet<MesaElectoral>();

    @MemberOrder(sequence = "1")
    public SortedSet<MesaElectoral> getMesas() {
        return mesas;
    }

    public void setMesas(final SortedSet<MesaElectoral> mesas) {
        this.mesas = mesas;
    }
    //endregion
*/
    @Override
    public int compareTo(final Circuito  other) {
        return ObjectContracts.compare(this, other, "distrito, seccion, nroCircuito");
    }
}
