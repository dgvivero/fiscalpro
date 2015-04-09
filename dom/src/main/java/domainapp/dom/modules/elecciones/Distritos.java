package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;

import java.util.List;

/**
 * Created by German on 09/04/2015.
 */
@DomainService(repositoryFor = AutoridadMesa.class)
@DomainServiceLayout(menuOrder = "50")
public class Distritos {

//region > listAll (action)
    @Action(semantics = SemanticsOf.SAFE )
    @ActionLayout(named = "Listado")
    @MemberOrder(sequence = "1")
    public List<Distrito> listAll() {
        QueryDefault<Distrito> query = QueryDefault.create(Distrito.class,"find");
        return container.allMatches(query);
    }
//endregion
//region > listar por dni (action)
    @ActionLayout(named = "Buscar por nombre")
    @MemberOrder(sequence = "1")
    public List<Distrito> listByDNI(final@ParameterLayout(named="Nombre") String nombre) {
        QueryDefault<Distrito> query = QueryDefault.create( Distrito.class,"findByName","nombreDistrito",nombre);
        List<Distrito> distritos = container.allMatches(query);
        if(distritos.isEmpty()) {
            container.informUser("No hay ningun Distrito Electoral con nombre:"+ nombre);
        }
        return distritos;
    }
//endregion

//region > create (action)
    @MemberOrder(sequence = "3")
    @ActionLayout(named = "Crear  Distrito")
    public Distrito create(
            final @ParameterLayout(named = "Nro de Distrito") int nroDistrito,
            final @ParameterLayout(named = "Nombre Distrito") String nombre) {

        final Distrito dt = container.newTransientInstance(Distrito.class);
        dt.setNroDistrito(nroDistrito);
        dt.setNombreDistrito(nombre);
        container.persistIfNotAlready(dt);
        return dt;
    }

//endregion



//region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;

//endregion
}
