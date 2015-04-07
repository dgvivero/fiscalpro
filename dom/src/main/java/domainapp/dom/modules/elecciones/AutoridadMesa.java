package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

/**
 * Created by German on 24/03/2015.
 */
@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id_autoridad    ")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.VERSION_NUMBER,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.AutoridadMesa "),
        @javax.jdo.annotations.Query(
                name = "findByDocument", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.AutoridadMesa "
                        + "WHERE nroDoc =:doc")
})
@javax.jdo.annotations.Unique(name="AUTORIDAD_UNQ", members = {"tipodoc","nrodoc"})
@DomainObject(
        objectType = "AUTORIDAD"
)

public class AutoridadMesa implements Comparable<AutoridadMesa> {

    public enum TipoDocumento {
        DNI, LE, LC, CI
    }

    public enum TipoAutoridad {
        Presidente, Vocal
    }

    //region > tipodoc (property)
    private TipoDocumento tipodoc;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "False")
    @Property(regexPattern = "^[0-9]")
    public TipoDocumento getTipoDocumento() {
        return tipodoc;
    }

    public void setTipoDocumento(final TipoDocumento tipodoc) {
        this.tipodoc = tipodoc;
    }

    //region > nroDoc (property)
    private Long nrodoc;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false")
    public Long getNroDocumento() {
        return nrodoc;
    }

    public void setNroDocumento(final Long nroDoc) {
        this.nrodoc = nroDoc;
    }
    //endregion

    //endregion

    //region > apellido (property)
    private String apellido;

    @MemberOrder(sequence = "3")
    @Column(allowsNull = "false")
    public String getApellido() {
        return apellido;
    }

    public void setApellido(final String apellido) {
        this.apellido = apellido;
    }
    //endregion

    //region > Nombre (property)
    private String nombre;

    @MemberOrder(sequence = "4")
    @Column(allowsNull = "False")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String Nombre) {
        this.nombre = Nombre;
    }
    //endregion

    //region > cargo (property)
    private TipoAutoridad cargo;

    @MemberOrder(sequence = "6")
    @Column(allowsNull = "false")
    public TipoAutoridad getCargo() {
        return cargo;
    }

    public void setCargo(final TipoAutoridad cargo) {
        this.cargo = cargo;
    }
    //endregion

    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        // TODO: append to org.apache.isis.applib.util.TitleBuffer, typically value properties
        buf.append(apellido);
        buf.append(", ");
        buf.append(nombre);
        buf.append(" - ");
        buf.append(cargo);
        return buf.toString();
    }

    @Override
    public int compareTo(final AutoridadMesa other) {
        return ObjectContracts.compare(this, other, "nrodoc");
    }

}
