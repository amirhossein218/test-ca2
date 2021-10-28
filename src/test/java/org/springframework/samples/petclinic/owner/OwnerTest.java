package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class OwnerTest {
//	private Owner owner;

	@Test
	public void addPetTestStateMustAddPet(){
		Pet pet = new Pet();
		Owner owner = new Owner();
		owner.addPet(pet);
		assertTrue(owner.getPets().contains(pet));
	}

	@Test
	public void addPetTestStateMustSetOwner(){
		Pet pet = new Pet();
		Owner owner = new Owner();
		owner.addPet(pet);
		assertEquals(pet.getOwner(),owner);
	}

	@Test
	public void addPetTestBehaviorMustAddPet(){
		Pet mockPet = mock(Pet.class);
		Owner owner = new Owner();
		when(mockPet.isNew()).thenReturn(true);

		owner.addPet(mockPet);


		assertTrue(owner.getPets().contains(mockPet));
	}

	@Test
	public void addPetTestBehaviorMustSetOwner(){
		Pet mockPet = mock(Pet.class);
		Owner owner = new Owner();
		when(mockPet.isNew()).thenReturn(true);

		owner.addPet(mockPet);
		verify(mockPet).setOwner(owner);
	}
}
