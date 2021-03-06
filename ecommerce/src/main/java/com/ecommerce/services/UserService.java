package com.ecommerce.services;

import com.ecommerce.domains.Address;
import com.ecommerce.domains.City;
import com.ecommerce.domains.User;
import com.ecommerce.domains.enums.Profile;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.dto.UserNewDTO;
import com.ecommerce.repositories.AddressRepository;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.security.UserPrincipal;
import com.ecommerce.services.exceptions.AuthorizationException;
import com.ecommerce.services.exceptions.DataIntegrityException;
import com.ecommerce.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private int size;

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUserAuthById(Long id) {
        validateUserAuth(id);
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado! id: " +
                id + ", Tipo: " + User.class.getName()));
    }

    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado! id: " +
                id + ", Tipo: " + User.class.getName()));
    }

    public User findUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado! email: " +
                email + ", Tipo: " + User.class.getName()));
    }

    public List<User> findCategoriesList() {
        return userRepository.findAll();
    }

    public Page<User> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),
                orderBy);
        return userRepository.findAll(pageRequest);
    }

    @Transactional
    public User createUser(User user) {
        user.setId(null);
        userRepository.save(user);
        addressRepository.saveAll(user.getAddressList());
        return user;
    }


    public User updaterUser(User user) {
        User newUser = findUserAuthById(user.getId());
        updateData(newUser, user);
        return userRepository.save(newUser);
    }

    public User updateUserPassword(User user) {
        User newUser = findUserById(user.getId());
        updateData(newUser, user);
        return userRepository.save(newUser);
    }

    public URI uploadUserProfilePicture(MultipartFile multipartFile) {
        UserPrincipal principal = UserSecurityService.authenticated();
        if (principal == null) {
            throw new AuthorizationException("Access denied");
        }

        BufferedImage jpgImage = imageService.getImageFromFile(multipartFile);

        jpgImage = imageService.chopImageShare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);

        String fileName = prefix + principal.getId() + ".jpg";

        URI uri = s3Service.uploadFile(multipartFile);
        User user = userRepository.findById(principal.getId()).get();
        user.setUserPictureURL(uri.toString());
        userRepository.save(user);
        return s3Service.
                uploadFile(fileName, "image", imageService.getInputStream(jpgImage, "jpg"));
    }

    public User fromDTO(UserDTO dto) {
        return new User(dto.getId(), dto.getName(), dto.getEmail(), null, null, null);
    }

    public User fromDTO(UserNewDTO dto) {
        User user = new User(null, dto.getName(),
                dto.getEmail(), dto.getUserCode(), passwordEncoder.encode(dto.getPassword()), dto.getProfiles());
        City city = new City(dto.getCityId(), null, null);
        Address  address = new Address(null, dto.getPlace(),
                dto.getNumber(), dto.getComplement(), dto.getNeighborhood(), dto.getPostCode(), user, city);
        user.getAddressList().add(address);
        user.getPhones().add(dto.getPhone1());
        if (dto.getPhone2() != null) {
            user.getPhones().add(dto.getPhone2());
        }
        if (dto.getPhone3() != null) {
            user.getPhones().add(dto.getPhone3());
        }
        return user;
    }

    public void deleteById(Long id) {
        findUserAuthById(id);
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir usuário porque possua entidades relacionadas");
        }
    }

    private void updateData(User newUser, User user){
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
    }

    private void validateUserAuth(Long id) {
        UserPrincipal principal = UserSecurityService.authenticated();
        if ((principal == null) || (!principal.hasRole(Profile.USER_ADMIN) && !id.equals(principal.getId()))) {
            throw new AuthorizationException("Acesso Negado");
        }
    }
}