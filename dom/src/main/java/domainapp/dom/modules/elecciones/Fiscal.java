package domainapp.dom.modules.elecciones;

import com.google.common.collect.Lists;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUsers;
import org.isisaddons.wicket.gmap3.cpt.applib.Locatable;
import org.isisaddons.wicket.gmap3.cpt.applib.Location;
import org.isisaddons.wicket.gmap3.cpt.service.LocationLookupService;

import javax.jdo.annotations.*;
import java.util.*;
import java.util.Collection;


/**
 * Created by German on 24/03/2015.
 */
@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy= IdGeneratorStrategy.NATIVE,
        column="id_fiscal")
@javax.jdo.annotations.Inheritance(
        strategy = InheritanceStrategy.NEW_TABLE)
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.Fiscal "),
        @javax.jdo.annotations.Query(
                name = "findByDocument", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.Fiscal "
                        + "WHERE nrodoc.startsWith(:doc)")
})
@javax.jdo.annotations.Unique(name="FISCAL_UNQ", members = {"tipodoc","nrodoc"})
@DomainObject(objectType = "FISCAL")
public class Fiscal extends ApplicationUser implements  Locatable {


    public enum TipoDocumento {
        DNI, LE, LC, CI
    }

    public enum TipoFiscal {
          Fiscal_Mesa
        , Fiscal_General ;
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

    //region > telefono (property)
    private String telefono;

    @MemberOrder(sequence = "5")
    @Column(allowsNull = "false")
    @Property(regexPattern = "^((?:\\(?\\d{3}\\)?[- .]?\\d{4}|\\(?\\d{4}\\)?[- .]?\\d{3}|\\(?\\d{5}\\)?[- .]?\\d{2})[- .]?\\d{4})$")
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(final String telefono) {
        this.telefono = telefono;
    }
    //endregion

    //region > cargo (property)
    private TipoFiscal cargo;

    @MemberOrder(sequence = "6")
    @Column(allowsNull = "false")
    public TipoFiscal getCargo() {
        return cargo;
    }

    public void setCargo(final TipoFiscal cargo) {
        this.cargo = cargo;
    }
    //endregion

    //region > mesaElectoral (property)
    private MesaElectoral mesaElectoral;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "true")
    public MesaElectoral getMesaElectoral() {
        return mesaElectoral;
    }

    public void setMesaElectoral(final MesaElectoral mesaElectoral) {
        this.mesaElectoral = mesaElectoral;
    }
    @Programmatic
    public List<MesaElectoral> autoCompleteMesaElectoral(final @Parameter(regexPattern = "/^[0-9]/") Integer nro) {
        return mesasElectorales.listByNroMesa(nro);
    }
    //endregion

    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        buf.append(apellido);
        buf.append(", ");
        buf.append(nombre);
        buf.append(" - ");
        buf.append(cargo);
        return buf.toString();
    }

    //region > location (property), Locatable impl
    private Double locationLatitude;
    private Double locationLongitude;
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @MemberOrder(sequence="3")
    public Location getLocation() {
        return locationLatitude != null && locationLongitude != null? new Location(locationLatitude, locationLongitude): null;
    }
    public void setLocation(final Location location) {
        locationLongitude = location != null ? location.getLongitude() : null;
        locationLatitude = location != null ? location.getLatitude() : null;
    }
    @MemberOrder(name="location", sequence="1")
    @ActionLayout(named = "Actualizar Localización")
    public Fiscal updateLocation(
            @ParameterLayout(named="Dirección")
            final String address) {
        final Location location = this.locationService.lookup(address);
        setLocation(location);
        return this;
    }
//endregion


    /*@Override
    public int compareTo(final Fiscal other) {
        return ObjectContracts.compare(this, other, "nrodoc");
    }*/


    //region > remove (action)
    @ActionLayout(named = "eliminar")
    @Action(invokeOn=InvokeOn.OBJECT_AND_COLLECTION)
    public List<Fiscal> remove() {
        container.removeIfNotAlready(this);
        List fiscales =fiscalesContainer.listAll();
        if(fiscales.isEmpty()) {
            container.informUser("No hay Fiscales cargados");
        }
        return fiscales;

    }
//endregion

//region > injected services

    @javax.inject.Inject
    Fiscales fiscalesContainer;

    @javax.inject.Inject
    DomainObjectContainer container;

    //Injections
    @javax.inject.Inject
    LocationLookupService locationService;

    @javax.inject.Inject
    MesasElectorales mesasElectorales;

    @javax.inject.Inject
    ApplicationUsers usuarios;

//endregion
}

