package com.sap.ibso.ato.training.tools.controller;

import com.sap.ibso.ato.training.tools.model.Advertisement;
import com.sap.ibso.ato.training.tools.repository.AdvertisementRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ads")
@Api(tags = { "ads" })
public class AdvertisementController {

	private AdvertisementRepository advertisementRepository;

	public AdvertisementController(AdvertisementRepository advertisementRepository) {
		this.advertisementRepository = advertisementRepository;
	}

	@GetMapping
	@ApiOperation(value = "Find all advertisements", nickname = "findAll")
	public Iterable<Advertisement> findAll() {
		return advertisementRepository.findAll();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Find advertisement by id", nickname = "findById")
	public Advertisement findById(@PathVariable("id") Long id) throws ObjectNotFoundException {
		Optional<Advertisement> advertisement = advertisementRepository.findById(id);
		if (!advertisement.isPresent()) {
			throw new ObjectNotFoundException(Advertisement.class, id);
		}
		return advertisement.get();
	}

	@PostMapping
	@ApiOperation(value = "Create advertisement", nickname = "create")
	public ResponseEntity<Advertisement> create(@RequestBody Advertisement advertisement) {
		advertisement = advertisementRepository.save(advertisement);
		return ResponseEntity
				.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + advertisement.getId()).build().toUri())
				.body(advertisement);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Update advertisement", nickname = "update")
	public Advertisement update(@RequestBody Advertisement advertisement, @PathVariable("id") Long id) throws ObjectNotFoundException, RequestInconsistentException {
		if (!id.equals(advertisement.getId())) {
			throw new RequestInconsistentException(String.format("Inconsistent IDs in url and body: url id: %d; body id: %d", advertisement.getId(), id));
		}
		if (!advertisementRepository.existsById(id)) {
			throw new ObjectNotFoundException(Advertisement.class, id);
		}
		return advertisementRepository.save(advertisement);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete advertisement", nickname = "delete")
	public void delete(@PathVariable("id") Long id) throws ObjectNotFoundException {
		if (!advertisementRepository.existsById(id)) {
			throw new ObjectNotFoundException(Advertisement.class, id);
		}
		advertisementRepository.deleteById(id);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete all advertisements", nickname = "deleteAll")
	public void deleteAll() {
		advertisementRepository.deleteAll();
	}

}
