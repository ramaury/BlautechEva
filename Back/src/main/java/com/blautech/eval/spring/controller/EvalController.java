package com.blautech.eval.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blautech.eval.spring.jpa.TaskJPA;
import com.blautech.eval.spring.jpa.UserJPA;
import com.blautech.eval.spring.model.Task;
import com.blautech.eval.spring.model.User;
import com.blautech.eval.spring.ws.request.ItemRequest;
import com.blautech.eval.spring.ws.request.SaveTaskRequest;
import com.blautech.eval.spring.ws.request.SaveUserRequest;
import com.blautech.eval.spring.ws.response.MessageResponse;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value="/api")
public class EvalController {

	@Autowired
	TaskJPA taskJPA;
	@Autowired
	UserJPA userJPA;


	@GetMapping(value="/getUserList")
	public ResponseEntity<List<User>> getUserList() {
		try {
			List<User> usersList = new ArrayList<User>();
			userJPA.findAll().forEach(usersList::add);

			if (usersList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(usersList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value="/getUserById")
	public ResponseEntity<User> getUserById(@RequestBody ItemRequest itemRequest) {
		Optional<User> userData = userJPA.findById(itemRequest.getId());

		if (userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value="/saveUser")
	public ResponseEntity<User> saveUser(@RequestBody SaveUserRequest user) {
		try {
			User savedUser = userJPA.save(new User(user.getName(), user.getFullname()));
			return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value="/editUser")
	public ResponseEntity<User> editUser(@RequestBody SaveUserRequest user) {
		try {
			if(user != null && user.getId() > 0){
				Optional<User> userData = userJPA.findById(user.getId());

				if (userData.isPresent()) {
					User userToEdit = userData.get();
					userToEdit.setName(user.getName());
					userToEdit.setFullname(user.getFullname());

					return new ResponseEntity<>(userJPA.save(userToEdit), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}		
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value="/deleteUser")
	public ResponseEntity<HttpStatus> deleteUser(@RequestBody ItemRequest deleteRequest) {
		try {		
			deleteUser(deleteRequest.getId());

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.out.println("sale :" + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	private void deleteUser(long idUser) {
		taskJPA.deleteTasks(idUser);
		userJPA.deleteById(idUser);
	}

	@PostMapping(value="/getTaskList")
	public ResponseEntity<List<Task>> getTaskList(@RequestBody ItemRequest itemRequest) {
		try {
			List<Task> tasksList = new ArrayList<Task>();
			if(itemRequest.getId() > 0){
				taskJPA.findByUser(itemRequest.getId()).forEach(tasksList::add);
			} else {
				taskJPA.findAll().forEach(tasksList::add);
			}			

			if (tasksList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tasksList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value="/getTaskById")
	public ResponseEntity<Task> getTaskById(@RequestBody ItemRequest itemRequest) {
		Optional<Task> taskData = taskJPA.findById(itemRequest.getId());

		if (taskData.isPresent()) {
			return new ResponseEntity<>(taskData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value="/saveTask")
	public ResponseEntity<Task> saveTask(@RequestBody SaveTaskRequest task) {
		try {
			if(task != null){
				if(task.getUser() > 0){
					Optional<User> userData = userJPA.findById(task.getUser());

					if (userData.isPresent()) {
						User user = userData.get();

						Task savedTask = taskJPA.save(new Task(user.getId(), task.getTitle(), task.getDescription(), task.getStatus()));
						return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
					} else{
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					}
				} else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value="/editTask")
	public ResponseEntity<Task> editTask(@RequestBody SaveTaskRequest task) {
		try {
			if(task != null && task.getId() > 0){
				Optional<Task> taskData = taskJPA.findById(task.getId());

				if(taskData.isPresent()){
					Task taskToEdit = taskData.get();
					if(task.getUser() != taskToEdit.getUser()){
						if(task.getUser() > 0){
							Optional<User> userData = userJPA.findById(task.getUser());

							if(userData.isPresent()){
								taskToEdit.setUser(userData.get().getId());
							} else {
								return new ResponseEntity<>(HttpStatus.NOT_FOUND);
							}
						} else {
							return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
						}
					}

					taskToEdit.setTitle(task.getTitle());
					taskToEdit.setDescription(task.getDescription());
					taskToEdit.setStatus(task.getStatus());

					return new ResponseEntity<>(taskJPA.save(taskToEdit), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}	
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value="/deleteTask")
	public ResponseEntity<?> authenticateUser(@RequestBody ItemRequest deleteRequest) {
		try {
			if(deleteRequest.getId() > 0){
				taskJPA.deleteById(deleteRequest.getId());

				return ResponseEntity.ok().body(new MessageResponse("Success"));
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

