package br.ufjf.tcc.controller;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zul.Checkbox;

import br.ufjf.tcc.business.PermissoesBusiness;
import br.ufjf.tcc.business.TipoUsuarioBusiness;
import br.ufjf.tcc.model.Permissoes;
import br.ufjf.tcc.model.TipoUsuario;

public class GerenciamentoPermissoesController extends CommonsController {
	private TipoUsuario selectedType;
	private TipoUsuarioBusiness tipoUsuarioBusiness = new TipoUsuarioBusiness();
	private List<TipoUsuario> types = tipoUsuarioBusiness.getTiposUsuarios();
	private List<Permissoes> permissions = new PermissoesBusiness()
			.getPermissoes();

	@Init
	public void init() {
		for (TipoUsuario type : types)
			tipoUsuarioBusiness.update(type);
		selectedType = types.get(0);
	}

	public List<TipoUsuario> getTypes() {
		return types;
	}

	public List<Permissoes> getPermissions() {
		return permissions;
	}

	public void setSelectedType(TipoUsuario selectedType) {
		this.selectedType = selectedType;
	}

	public TipoUsuario getSelectedType() {
		return selectedType;
	}

	@Command("verify")
	public void verify(@BindingParam("permissionName") String permissionName,
			@BindingParam("checkbox") Checkbox checkbox) {
		for (Permissoes aux : selectedType.getPermissoes())
			if (aux.getNomePermissao().equals(permissionName)) {
				checkbox.setChecked(true);
				break;
			}

	}

	@Command("edit")
	public void edit(@BindingParam("permission") Permissoes permission,
			@BindingParam("checkbox") Checkbox checkbox) {
		if (checkbox.isChecked())
			selectedType.getPermissoes().add(permission);			
		else {
			for (Permissoes aux : selectedType.getPermissoes())
				if (aux.getNomePermissao()
						.equals(permission.getNomePermissao())) {
					selectedType.getPermissoes().remove(aux);
					break;
				}
		}
		
		tipoUsuarioBusiness.editar(selectedType);

	}

	@Command("refreshPermissionsList")
	public void RefreshPermissionsList() {
		BindUtils.postNotifyChange(null, null, this, "permissions");
	}

}