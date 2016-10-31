package com.main;

import static spark.Spark.*;

import com.model.ServiceTodoJPA;

public class TodoRest {
	public static void main(String[] args) {
		//JPA SERVICE
		ServiceTodoJPA service = new ServiceTodoJPA();
		//enabled access from client
		enableCORS();
		
		//Retrieves todo list
		get("/todo", (request, response) -> {
		    return service.getTodos();
		}, new JsonTransformer());

		//Create todo
		post("/todo", (request, response) -> {
		    service.createTodo(request.queryParams("todotext"));
		    return "OK";
		});

		//Set todo completed 
		put("/todo", (request, response) -> {
			service.completeTodo(Integer.parseInt(request.queryParams("id")));
			return "OK";
		});

		//delete todo
		delete("/todo", (request, response) -> {
			service.deleteTodo(Integer.parseInt(request.queryParams("id")));
			return "OK";
		});
    }
	
	// Enables CORS on requests.
	private static void enableCORS() {

	    options("/*", (request, response) -> {
	    	
	        String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
	        if (accessControlRequestHeaders != null) {
	            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
	        }

	        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
	        if (accessControlRequestMethod != null) {
	            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
	        }

	        return "OK";
	    });

	    before((request, response) -> {
	    	//grant access to client app
	        response.header("Access-Control-Allow-Origin", "http://localhost:5500");	        
	    });
	}
}
