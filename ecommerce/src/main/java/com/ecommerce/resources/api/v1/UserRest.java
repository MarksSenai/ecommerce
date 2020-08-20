package com.ecommerce.resources.api.v1;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.ecommerce.domains.User;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.dto.UserNewDTO;
import com.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(RestPath.BASE_PATH_V1 + "/users")
public class UserRest {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> findUsers() {
        List<User> users = userService.findUsers();
        List<UserDTO> userDTOS = users.stream().map(obj -> new UserDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(userDTOS);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<UserDTO>> findPages(
            @RequestParam(value="page", defaultValue = "0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value="direction", defaultValue = "ASC") String direction) {
        Page<User> users = userService.findPage(page, linesPerPage, orderBy, direction);
        Page<UserDTO> userDTOS = users.map(obj -> new UserDTO(obj));
        return ResponseEntity.ok().body(userDTOS);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findUserById(id));
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity<User> create(@Valid @RequestBody UserDTO categoryDTO) {
//        User category = userService.fromDTO(categoryDTO);
//        category = userService.createUser(category);
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}").buildAndExpand(category.getId()).toUri();
//        return ResponseEntity.created(uri).build();
//    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity<User> insert(@Valid @RequestBody UserNewDTO userNewDTO) {
        User category = userService.fromDTO(userNewDTO);
        category = userService.createUser(category);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<User> update(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id) {
        User user    = userService.fromDTO(userDTO);
        user.setId(id);
        userService.updaterUser(user);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}