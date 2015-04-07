package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

/**
 * Created by German on 24/03/2015.
 */

@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id_distrito")
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
                        + "FROM domainapp.dom.modules.elecciones.Distrito"
                        + "WHERE nombreDistrito =:nombreDistrito")
})
@javax.jdo.annotations.Unique(name="DISTRITO_UNQ", members = {"nrodistrito"})
@DomainObject(
        objectType = "DISTRITO"
)
public class Distrito implements Comparable<Distrito>{

    //region > nrodistrito (property)
    private int nrodistrito;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "False")
    public int getNroDistrito() {
        return nrodistrito;
    }

    public void setNroDistrito(final int nrodistrito) {
        this.nrodistrito = nrodistrito;
    }

    //endregion
    //region > nombreDistrito (property)
    private String nombreDistrito;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false")
    @Title
    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(final String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }
    //endregion

    //region > compareTo

    @Override
    public int compareTo(final Distrito other) {
        return ObjectContracts.compare(this, other, "nrodistrito");
    }

    //endregion


}