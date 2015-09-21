package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.role.ApplicationRoles;
import org.isisaddons.module.security.dom.user.AccountType;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUserStatus;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by German on 24/03/2015.
 */
@DomainService(repositoryFor = Fiscal.class)
@DomainServiceLayout(menuOrder = "10")
public class Fiscales {

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado"
    )
    @MemberOrder(sequence = "1")
    public List<Fiscal> listAll() {
        QueryDefault<Fiscal> query =
                QueryDefault.create(
                        Fiscal.class,
                        "find");

        return container.allMatches(query);
    }
    //endregion

    //region > listar por dni (action)
    @ActionLayout(named = "Buscar por dni")
    @MemberOrder(sequence = "1")
    public List<Fiscal> listByDNI(final@ParameterLayout(named="Nro de Documento") Long dni) {
        QueryDefault<Fiscal> query = QueryDefault.create( Fiscal.class,"findByDocument","doc",dni);
        List<Fiscal> fiscales = container.allMatches(query);
        if(fiscales.isEmpty()) {
            container.informUser("No hay ningun fiscal con DNI:"+ dni);
        }
        return fiscales;
    }
    //endregion

    //region > create (action)
    @MemberOrder(sequence = "3")
    @ActionLayout(named = "Crear")
    public Fiscal create(
            final @ParameterLayout(named="Tipo de Documento")Fiscal.TipoDocumento tdoc,
            final @ParameterLayout(named = "Nro de Documento") Long nrodoc,
            final @ParameterLayout(named = "Apellido") String apellido,
            final @ParameterLayout(named="Nombre") String nombre,
            final @ParameterLayout(named="Telefono") String telefono,
            final @ParameterLayout(named = "Cargo") Fiscal.TipoFiscal tf,
            @Parameter(maxLength = ApplicationUser.MAX_LENGTH_USERNAME)
            @ParameterLayout(named="Usuario")
            final String username,
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named="Rol inicial")
            final ApplicationRole initialRole,
            final @ParameterLayout(named="Contraseña") String passwd) {

        final Fiscal f = container.newTransientInstance(Fiscal.class);
        f.setTipoDocumento(tdoc);
        f.setNroDocumento(nrodoc);
        f.setApellido(apellido);
        f.setNombre(nombre);
        f.setTelefono(telefono);
        f.setCargo(tf);
        f.setUsername(username);
        f.setStatus(ApplicationUserStatus.ENABLED);
        f.setAccountType(AccountType.LOCAL);
        if(initialRole != null) {
            f.addRole(initialRole);
        }

        f.setEncryptedPassword(passwd);
        container.persistIfNotAlready(f);
        return f;
    }

    public ApplicationRole default7Create() {
        return applicationRoles.findRoleByName(IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME);
    }



    //endregion


    //region > injected services

    @Inject
    DomainObjectContainer container;
    @Inject
    ApplicationRoles applicationRoles;

    //endregion
}
