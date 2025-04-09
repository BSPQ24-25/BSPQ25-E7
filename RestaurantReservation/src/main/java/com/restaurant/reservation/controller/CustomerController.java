package com.restaurant.reservation.controller;

public class CustomerController {

    //Habra que añadir los metodos de reservar, modificar y eliminar una mesa de un restaurante 
    //Pasos a seguir (Mas o menos):
    //1. Crear los metodos del controller GET, POST, PUT, DELETE y estos llamaran al service CustomerService.
    //2. Crear la logica: En el customerservice hay que implementar la logica de los metodos del controller
    //3. Es posible que haya que crear dtos que son informacion encapsulada de una clase para que se comunique informacion necesaria entre el service y controller. Por ejemplo un dto para el login llevara el mail y la contraseña no hace falta darle mas informacion
    //4. Una vez que funcione y quieras implementarlo a una interfaz en un html, en pagecontroller se encargar de manejar los htmls asi que tendras que meter uno si creas un html
    //EJEMPLO FLUJO:
    //Cliente -> POST /reservar -> CustomerController -> ReservationService -> ReservationRepository -> BD
    
}
