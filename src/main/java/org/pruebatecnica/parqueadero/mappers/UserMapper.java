package org.pruebatecnica.parqueadero.mappers;


import org.pruebatecnica.parqueadero.dtos.AuthorizationRequest;
import org.pruebatecnica.parqueadero.dtos.UserResponse;
import org.pruebatecnica.parqueadero.entities.Usuario;

public class UserMapper {

	private UserMapper() {
	}

	public static UserResponse toResponse(Usuario user) {
		return UserResponse.builder().name(user.getEmail()).id(Integer.parseInt(user.getDocumento())).build();
	}

	public static Usuario toDomain(AuthorizationRequest authorizationRequest) {
		return Usuario.builder().email(authorizationRequest.getUserName()).password(authorizationRequest.getPassword())
				.build();
	}
}
