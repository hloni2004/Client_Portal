# API Testing Guide - JSON Request Examples for Postman

All endpoints have been converted to use `@RequestBody` with JSON payloads instead of `@RequestParam`.

---

## 🔐 UserController (`/api/users`)

### 1. Register User
**POST** `/api/users/register`
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "SecurePass123",
  "role": "CLIENT"
}
```
**Notes:** `role` values: `CLIENT`, `ADMIN`, `DEVELOPER`

---

### 2. Login
**POST** `/api/users/login`
```json
{
  "email": "john.doe@example.com",
  "password": "SecurePass123"
}
```

---

### 3. Search Users by Name
**POST** `/api/users/search`
```json
{
  "query": "John"
}
```

---

### 4. Update User Profile
**PUT** `/api/users/{id}/profile`
```json
{
  "name": "John Doe Updated",
  "companyName": "Tech Solutions Inc",
  "phone": "+27123456789"
}
```
**Notes:** `companyName` and `phone` are optional

---

### 5. Change Password
**PUT** `/api/users/{id}/password`
```json
{
  "oldPassword": "SecurePass123",
  "newPassword": "NewSecurePass456"
}
```

---

## 📋 ProjectController (`/api/projects`)

### 1. Create Project
**POST** `/api/projects/create`
```json
{
  "clientId": 1,
  "title": "E-Commerce Website",
  "description": "Build a full-featured e-commerce platform",
  "startDate": "2025-01-15",
  "dueDate": "2025-06-30"
}
```
**Notes:** Date format must be `yyyy-MM-dd`

---

### 2. Search Projects by Title
**POST** `/api/projects/search`
```json
{
  "query": "E-Commerce"
}
```

---

### 3. Get Projects Due Between Dates
**POST** `/api/projects/due-between`
```json
{
  "startDate": "2025-01-01",
  "endDate": "2025-12-31"
}
```

---

### 4. Update Project Status
**PUT** `/api/projects/{id}/status`
```json
{
  "status": "IN_PROGRESS"
}
```
**Notes:** `status` values: `NOT_STARTED`, `IN_PROGRESS`, `ON_HOLD`, `COMPLETED`, `CANCELLED`

---

### 5. Update Project Progress
**PUT** `/api/projects/{id}/progress`
```json
{
  "progress": 45.5
}
```
**Notes:** Progress must be between 0.0 and 100.0

---

## ✅ TaskController (`/api/tasks`)

### 1. Create Task
**POST** `/api/tasks/create`
```json
{
  "title": "Design Database Schema",
  "description": "Create ER diagram and design normalized database tables",
  "projectId": 1,
  "assignedToId": 5,
  "dueDate": "2025-02-15"
}
```

---

### 2. Search Tasks by Title
**POST** `/api/tasks/search`
```json
{
  "query": "Database"
}
```

---

### 3. Update Task Status
**PUT** `/api/tasks/{id}/status`
```json
{
  "status": "IN_PROGRESS"
}
```
**Notes:** `status` values: `TODO`, `IN_PROGRESS`, `IN_REVIEW`, `COMPLETED`, `BLOCKED`

---

### 4. Assign Task to User
**PUT** `/api/tasks/{id}/assign`
```json
{
  "userId": 7
}
```

---

## 👥 ProjectUserController (`/api/project-users`)

### 1. Add User to Project
**POST** `/api/project-users/add`
```json
{
  "projectId": 1,
  "userId": 3,
  "role": "DEVELOPER"
}
```
**Notes:** `role` values: `OWNER`, `ADMIN`, `DEVELOPER`, `VIEWER`

---

### 2. Check if User Exists in Project
**POST** `/api/project-users/exists`
```json
{
  "projectId": 1,
  "userId": 3
}
```

---

### 3. Change User Role in Project
**PUT** `/api/project-users/change-role`
```json
{
  "projectId": 1,
  "userId": 3,
  "newRole": "ADMIN"
}
```

---

### 4. Remove User from Project
**DELETE** `/api/project-users/remove`
```json
{
  "projectId": 1,
  "userId": 3
}
```

---

## 🔔 NotificationController (`/api/notifications`)

### 1. Create Notification
**POST** `/api/notifications/create`
```json
{
  "message": "Your project has been approved",
  "type": "PROJECT_UPDATE",
  "userId": 5
}
```
**Notes:** `type` values: `PROJECT_UPDATE`, `TASK_ASSIGNED`, `DEADLINE_REMINDER`, `FEEDBACK_RECEIVED`, `DELIVERABLE_APPROVED`, `SYSTEM_ALERT`

---

## 💬 FeedbackController (`/api/feedback`)

### 1. Add Feedback
**POST** `/api/feedback/add`
```json
{
  "message": "Great work! Please update the color scheme as discussed.",
  "deliverableId": 2,
  "userId": 1
}
```

---

## 📦 DeliverableController (`/api/deliverables`)

### 1. Upload Deliverable
**POST** `/api/deliverables/upload`
```json
{
  "fileName": "homepage-mockup.pdf",
  "fileType": "PDF",
  "fileUrl": "https://storage.example.com/files/homepage-mockup.pdf",
  "projectId": 1
}
```

---

### 2. Search Deliverables by File Name
**POST** `/api/deliverables/search`
```json
{
  "query": "mockup"
}
```

---

## 📝 Validation Rules

All DTOs include validation:
- `@NotBlank` - Field cannot be empty or null
- `@NotNull` - Field cannot be null
- `@Email` - Must be a valid email format
- `@DecimalMin/@DecimalMax` - Number range validation

### Common Validation Errors Response:
```json
{
  "timestamp": "2025-11-28T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": [
    {
      "field": "email",
      "message": "Email must be valid"
    }
  ]
}
```

---

## 🎯 Testing Tips

1. **Headers**: Set `Content-Type: application/json` for all requests
2. **Authentication**: Add authorization headers if your app uses security
3. **Path Variables**: Replace `{id}` in URLs with actual IDs (e.g., `/api/users/5/profile`)
4. **Date Format**: Always use `yyyy-MM-dd` for dates
5. **Enum Values**: Use exact uppercase values (e.g., `IN_PROGRESS`, not `in_progress`)

---

## ⚠️ Important Changes from Old API

| Endpoint | Old Method | New Method | Notes |
|----------|-----------|-----------|-------|
| `/api/users/search` | GET with `?name=` | POST with JSON | Changed to POST |
| `/api/projects/search` | GET with `?title=` | POST with JSON | Changed to POST |
| `/api/projects/due-between` | GET with `?startDate=&endDate=` | POST with JSON | Changed to POST |
| `/api/tasks/search` | GET with `?title=` | POST with JSON | Changed to POST |
| `/api/project-users/exists` | GET with `?projectId=&userId=` | POST with JSON | Changed to POST |
| `/api/deliverables/search` | GET with `?fileName=` | POST with JSON | Changed to POST |

---

## 🚀 Quick Test Collection Order

Test in this order to ensure dependencies:
1. Register a user → Login
2. Create a project
3. Add user to project
4. Create a task
5. Upload a deliverable
6. Add feedback
7. Create a notification

---

**All endpoints now use JSON bodies instead of query parameters!**
**Service layer remains unchanged - only controllers and DTOs were modified.**
