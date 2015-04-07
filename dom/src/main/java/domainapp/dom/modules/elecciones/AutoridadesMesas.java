package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;

import java.util.List;

/**
 * Created by German on 24/03/2015.
 */

@DomainService(repositoryFor = AutoridadMesa.class)
@DomainServiceLayout(menuOrder = "20")
public class AutoridadesMesas {

    //region > listAll (action)
    @Action(semantics = SemanticsOf.SAFE )
    @ActionLayout(named = "Listado")
    @MemberOrder(sequence = "1")
    public List<AutoridadMesa> listAll() {
        QueryDefault<AutoridadMesa> query = QueryDefault.create(AutoridadMesa.class,"find");
        return container.allMatches(query);
    }
    //endregion

    //region > create (action)
    @MemberOrder(sequence = "3")
    @ActionLayout(named = "Crear")
    public AutoridadMesa create(
            final @ParameterLayout(named="Tipo de Documento")AutoridadMesa.TipoDocumento tdoc,
            final @ParameterLayout(named = "Nro de Documento") Long nrodoc,
            final @ParameterLayout(named = "Apellido") String apellido,
            final @ParameterLayout(named="Nombre") String nombre,
            final @ParameterLayout(named="Telefono") String telefono,
            final @ParameterLayout(named = "Cargo") AutoridadMesa.TipoAutoridad ta) {

        final AutoridadMesa am = container.newTransientInstance(AutoridadMesa.class);
        am.setTipoDocumento(tdoc);
        am.setNroDocumento(nrodoc);
        am.setApellido(apellido);
        am.setNombre(nombre);
        am.setCargo(ta);
        container.persistIfNotAlready(am);
        return am;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;

    //endregion
}
