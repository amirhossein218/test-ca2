package org.springframework.samples.petclinic.owner;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.samples.petclinic.visit.Visit;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import java.time.LocalDate;
import java.util.*;
import org.slf4j.Logger;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PetManagerTest {
	@Mock
	OwnerRepository ownerRepository;
	@Mock
	PetTimedCache petTimedCache;
	@Mock
	Logger logger;
	private PetManager petManager;


	/*
	 * Dummy - Mock Object - Mockist
	 * verification method : state
	 */



	@Test
	public void findOwner() {
		petManager = new PetManager(petTimedCache, ownerRepository, logger);
		Owner temp = new Owner();
		temp.setId(1);
		when(ownerRepository.findById(1)).thenReturn(temp);
		assertEquals(temp, petManager.findOwner(1));
	}

	/*
	 * Dummy Object - Mockist -
	 * verification method = state
	 */



	@Test
	public void newPet() {
		petManager = new PetManager(petTimedCache, ownerRepository, logger);
		Owner temp = new Owner();
		temp.setId(1);
		Owner expectedOwner = petManager.newPet(temp).getOwner();
		assertEquals(temp,expectedOwner);
	}

	/*
	 * Dummy & Mock Object -  Mockist
	 * verification method state
	 */

	@Test
	public void findPet() {
		petManager = new PetManager(petTimedCache,ownerRepository,logger);
		Pet pet = new Pet();
		pet.setId(1);
		when(petTimedCache.get(1)).thenReturn(pet);
		assertEquals(pet,petManager.findPet(1));
	}

	/*
	 * Dummy Object - Spy - Mockist
	 * verification method behavior
	 */

	@Test
	public void savePet() {
		PetManager petManager = new PetManager(petTimedCache,ownerRepository,logger);
		Owner dumowner = mock(Owner.class);
		Pet dumpet = mock(Pet.class);
		petManager.savePet(dumpet, dumowner);
		verify(petTimedCache).save(dumpet);
	}

	/*
	 * stub - dummy  - Mockist
	 * verification method state
	 */

	@Test
	public void getOwnerPets() {
		PetManager petManager = new PetManager(petTimedCache,ownerRepository,logger);
		Owner owner = new Owner();
		owner.setId(1);
		Pet pet1 = new Pet();
		Pet pet2 = new Pet();
		Pet pet3 = new Pet();
		pet1.setId(1);
		pet2.setId(2);
		pet3.setId(3);
		Set<Pet> petset = new HashSet<>();
		petset.add(pet1);
		petset.add(pet2);
		petset.add(pet3);
		owner.setPetsInternal(petset);
		when(ownerRepository.findById(1)).thenReturn(owner);
		List<Pet> expectedPest = new ArrayList<>(petset);
		assertEquals(expectedPest,petManager.getOwnerPets(owner.getId()));
	}

	/*
	 *  Dummy Object - Test Stub - Test Spy - Mockist
	 *  verification method : state
	 */

	@Test
	public void getOwnerPetTypes() {
		PetManager petManager = new PetManager(petTimedCache,ownerRepository,logger);
		Owner owner = new Owner();
		owner.setId(0);
		Set<PetType> pettypesset = new HashSet<>();
		Set<Pet> petset = new HashSet<>();

		PetType pt1 = new PetType();
		pt1.setId(1);
		Pet pet1 = new Pet();
		pet1.setType(pt1);
		pet1.setId(1);
		pettypesset.add(pt1);
		petset.add(pet1);
		when(ownerRepository.findById(1)).thenReturn(owner);

		PetType pt2 = new PetType();
		pt2.setId(2);
		Pet pet2 = new Pet();
		pet2.setType(pt2);
		pet2.setId(2);
		pettypesset.add(pt2);
		petset.add(pet2);
		when(ownerRepository.findById(2)).thenReturn(owner);

		owner.setPetsInternal(petset);

		assertEquals(pettypesset, petManager.getOwnerPetTypes(pet1.getId()));
		assertEquals(pettypesset, petManager.getOwnerPetTypes(pet2.getId()));
	}

	/*
	 * Dummy Object - Test Stub - Test Spy - Mockist;
	 * verification method : state
	 */

	@Test
	public void getVisitBetween() {
		LoggerConfig lConfig = new LoggerConfig();
		logger = lConfig.getLogger();
		PetTimedCache mockPetTimedCache = mock(PetTimedCache.class);
		petManager = new PetManager(mockPetTimedCache, ownerRepository, logger);
//		int petId = 1;
		Pet pet = new Pet();
		pet.setId(1);

		when(mockPetTimedCache.get(2)).thenReturn(pet);

		LocalDate end = LocalDate.now();
		Visit visit = new Visit();
		visit.setDate(end.minusYears(2));
		pet.addVisit(visit);
		Visit visit1 = new Visit();
		visit1.setDate(end.minusMonths(1));
		pet.addVisit(visit1);
		Visit visit2 = new Visit();
		visit2.setDate(end.minusMonths(5));
		pet.addVisit(visit2);
		List<Visit> visits = new ArrayList<Visit>();
		visits.add(visit);
		visits.add(visit1);
		visits.add(visit2);
		LocalDate start = end.minusYears(4);


		assertEquals(petManager.getVisitsBetween(2, start, end), visits);
	}





}
