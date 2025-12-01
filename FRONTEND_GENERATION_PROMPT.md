# Complete Frontend Generation Prompt for Client Portal System

## 🎯 Project Overview

Build a **full-stack Client Portal Web Application** using **React/Next.js** with **TypeScript** and **Tailwind CSS**. This is a project management system where clients can view projects, track tasks, submit deliverables, receive notifications, and provide feedback.

### **Tech Stack Requirements**
- **Framework**: Next.js 14+ (App Router) with TypeScript
- **Styling**: Tailwind CSS + shadcn/ui components
- **State Management**: Zustand or Redux Toolkit
- **API Client**: Axios with interceptors
- **Form Handling**: React Hook Form + Zod validation
- **File Upload**: React Dropzone
- **Authentication**: JWT stored in HTTP-only cookies or localStorage
- **Routing**: Next.js App Router with protected routes
- **Notifications**: React Hot Toast or Sonner

---

## 🏗️ System Architecture

### **User Roles**
1. **CLIENT** - Can view assigned projects, upload deliverables, provide feedback
2. **DEVELOPER** - Can manage tasks, update status, view all projects
3. **ADMIN** - Full system access, user management, project creation

### **Core Modules**
1. Authentication & User Management
2. Project Management
3. Task Management
4. Deliverable Management
5. Feedback System
6. Notification System
7. Team Collaboration (ProjectUser)

---

## 📡 Backend API Base URL

```
BASE_URL: http://localhost:8080/api
```

All requests must include:
- `Content-Type: application/json`
- `Authorization: Bearer {token}` (for protected routes)

---

## 🔐 1. AUTHENTICATION & USER MANAGEMENT

### **API Endpoints**

#### **POST /api/users/register**
Register a new user account.

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "SecurePass123",
  "role": "CLIENT"
}
```

**Response (201):**
```json
{
  "userId": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "role": "CLIENT",
  "companyName": null,
  "phone": null,
  "createdAt": "2025-11-28T10:30:00"
}
```

**Validation Rules:**
- Name: Required, min 2 characters
- Email: Required, valid email format
- Password: Required, min 8 characters, must contain uppercase, lowercase, number
- Role: Required, enum (CLIENT, DEVELOPER, ADMIN)

---

#### **POST /api/users/login**
Authenticate user and receive JWT token.

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "SecurePass123"
}
```

**Response (200):**
```json
{
  "userId": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "role": "CLIENT",
  "companyName": "Tech Solutions Inc",
  "phone": "+27123456789",
  "lastLogin": "2025-11-28T10:30:00",
  "createdAt": "2025-11-20T08:00:00"
}
```

**Note:** Backend returns user object. Frontend must generate/store JWT token for subsequent requests.

---

#### **GET /api/users/{id}**
Get user details by ID.

**Response (200):**
```json
{
  "userId": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "role": "CLIENT",
  "companyName": "Tech Solutions Inc",
  "phone": "+27123456789",
  "lastLogin": "2025-11-28T10:30:00",
  "createdAt": "2025-11-20T08:00:00"
}
```

---

#### **GET /api/users**
Get all users (ADMIN only).

**Response (200):**
```json
[
  {
    "userId": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "role": "CLIENT",
    "companyName": "Tech Solutions Inc",
    "phone": "+27123456789"
  }
]
```

---

#### **POST /api/users/search**
Search users by name.

**Request Body:**
```json
{
  "query": "John"
}
```

**Response (200):**
```json
[
  {
    "userId": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "role": "CLIENT"
  }
]
```

---

#### **PUT /api/users/{id}/profile**
Update user profile information.

**Request Body:**
```json
{
  "name": "John Doe Updated",
  "companyName": "Tech Solutions Inc",
  "phone": "+27123456789"
}
```

**Response (200):** Empty body with 200 status

---

#### **PUT /api/users/{id}/password**
Change user password.

**Request Body:**
```json
{
  "oldPassword": "SecurePass123",
  "newPassword": "NewSecurePass456"
}
```

**Response (200):** Empty body with 200 status

---

#### **DELETE /api/users/{id}**
Delete user account (ADMIN only).

**Response (204):** No content

---

#### **GET /api/users/count**
Get total user count.

**Response (200):**
```json
42
```

---

#### **GET /api/users/exists/{email}**
Check if email exists in system.

**Response (200):**
```json
true
```

---

### **Frontend UI Requirements for Authentication**

#### **Pages:**
1. **/auth/login** - Login page
2. **/auth/register** - Registration page
3. **/profile** - User profile view/edit
4. **/profile/change-password** - Password change form

#### **Components:**
- `LoginForm` - Email + password fields, "Remember me" checkbox, "Forgot password" link
- `RegisterForm` - Name, email, password, confirm password, role selection
- `ProfileCard` - Display user info with edit button
- `ProfileEditForm` - Inline or modal edit for name, company, phone
- `ChangePasswordForm` - Old password, new password, confirm password

#### **State Management:**
```typescript
interface AuthState {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  login: (email: string, password: string) => Promise<void>;
  register: (data: RegisterDto) => Promise<void>;
  logout: () => void;
  updateProfile: (userId: number, data: ProfileUpdateDto) => Promise<void>;
  changePassword: (userId: number, data: PasswordChangeDto) => Promise<void>;
}
```

#### **Validation:**
- Email: Valid format, max 255 chars
- Password: Min 8 chars, 1 uppercase, 1 lowercase, 1 number
- Name: Min 2 chars, max 100 chars
- Phone: Optional, format validation (+27 for South Africa)

---

## 📋 2. PROJECT MANAGEMENT

### **API Endpoints**

#### **POST /api/projects**
Create new project (raw entity - use /create instead).

#### **POST /api/projects/create**
Create new project with details.

**Request Body:**
```json
{
  "clientId": 1,
  "title": "E-Commerce Website",
  "description": "Build a full-featured e-commerce platform",
  "startDate": "2025-01-15",
  "dueDate": "2025-06-30"
}
```

**Response (201):**
```json
{
  "projectId": 1,
  "clientId": 1,
  "title": "E-Commerce Website",
  "description": "Build a full-featured e-commerce platform",
  "startDate": "2025-01-15",
  "dueDate": "2025-06-30",
  "status": "NOT_STARTED",
  "progress": 0.0
}
```

**Status Enum:** NOT_STARTED, IN_PROGRESS, ON_HOLD, COMPLETED, CANCELLED

---

#### **GET /api/projects/{id}**
Get project details by ID.

**Response (200):**
```json
{
  "projectId": 1,
  "clientId": 1,
  "title": "E-Commerce Website",
  "description": "Build a full-featured e-commerce platform",
  "startDate": "2025-01-15",
  "dueDate": "2025-06-30",
  "status": "IN_PROGRESS",
  "progress": 45.5
}
```

---

#### **GET /api/projects**
Get all projects.

**Response (200):** Array of projects

---

#### **GET /api/projects/client/{clientId}**
Get all projects for specific client.

**Response (200):** Array of projects

---

#### **GET /api/projects/status/{status}**
Get projects by status (NOT_STARTED, IN_PROGRESS, etc.).

**Response (200):** Array of projects

---

#### **GET /api/projects/client/{clientId}/status/{status}**
Get client projects filtered by status.

**Response (200):** Array of projects

---

#### **POST /api/projects/search**
Search projects by title.

**Request Body:**
```json
{
  "query": "E-Commerce"
}
```

**Response (200):** Array of projects

---

#### **GET /api/projects/overdue**
Get all overdue projects.

**Response (200):** Array of projects

---

#### **POST /api/projects/due-between**
Get projects due within date range.

**Request Body:**
```json
{
  "startDate": "2025-01-01",
  "endDate": "2025-12-31"
}
```

**Response (200):** Array of projects

---

#### **PUT /api/projects/{id}**
Update entire project.

**Request Body:** Full project object

**Response (200):** Updated project

---

#### **PUT /api/projects/{id}/status**
Update project status only.

**Request Body:**
```json
{
  "status": "IN_PROGRESS"
}
```

**Response (200):** Empty body with 200 status

---

#### **PUT /api/projects/{id}/progress**
Update project progress percentage.

**Request Body:**
```json
{
  "progress": 45.5
}
```

**Response (200):** Empty body with 200 status

**Validation:** Progress must be between 0.0 and 100.0

---

#### **DELETE /api/projects/{id}**
Delete project (ADMIN only).

**Response (204):** No content

---

#### **GET /api/projects/count**
Get total project count.

**Response (200):**
```json
15
```

---

### **Frontend UI Requirements for Projects**

#### **Pages:**
1. **/projects** - Projects list/grid view
2. **/projects/{id}** - Project detail page
3. **/projects/new** - Create new project (ADMIN)
4. **/projects/{id}/edit** - Edit project (ADMIN)

#### **Components:**
- `ProjectCard` - Card showing title, status, progress bar, due date
- `ProjectList` - Grid or list of ProjectCards with filters
- `ProjectFilters` - Filter by status, client, date range
- `ProjectCreateForm` - Form for creating new project
- `ProjectEditForm` - Edit existing project
- `ProjectStatusBadge` - Color-coded status indicator
- `ProjectProgressBar` - Visual progress indicator
- `ProjectOverview` - Stats cards (total, in progress, completed, overdue)
- `ProjectTimeline` - Visual timeline of project dates

#### **State Management:**
```typescript
interface ProjectState {
  projects: Project[];
  currentProject: Project | null;
  loading: boolean;
  filters: ProjectFilters;
  fetchProjects: () => Promise<void>;
  fetchProjectById: (id: number) => Promise<void>;
  createProject: (data: ProjectCreateDto) => Promise<void>;
  updateProject: (id: number, data: Project) => Promise<void>;
  updateProjectStatus: (id: number, status: ProjectStatus) => Promise<void>;
  updateProjectProgress: (id: number, progress: number) => Promise<void>;
  deleteProject: (id: number) => Promise<void>;
  searchProjects: (query: string) => Promise<void>;
}
```

#### **Business Rules:**
- Progress can only be updated by ADMIN or DEVELOPER
- Status transitions: NOT_STARTED → IN_PROGRESS → COMPLETED
- Can put project ON_HOLD from any status
- Show warning badge if project is overdue
- Calculate days remaining/overdue automatically
- Client users can only see their own projects
- ADMIN/DEVELOPER can see all projects

---

## ✅ 3. TASK MANAGEMENT

### **API Endpoints**

#### **POST /api/tasks**
Create raw task entity (use /create instead).

#### **POST /api/tasks/create**
Create new task.

**Request Body:**
```json
{
  "title": "Design Database Schema",
  "description": "Create ER diagram and design normalized database tables",
  "projectId": 1,
  "assignedToId": 5,
  "dueDate": "2025-02-15"
}
```

**Response (201):**
```json
{
  "taskId": 1,
  "title": "Design Database Schema",
  "description": "Create ER diagram and design normalized database tables",
  "projectId": 1,
  "assignedToId": 5,
  "dueDate": "2025-02-15",
  "status": "TODO"
}
```

**Status Enum:** TODO, IN_PROGRESS, IN_REVIEW, COMPLETED, BLOCKED

---

#### **GET /api/tasks/{id}**
Get task by ID.

**Response (200):** Task object

---

#### **GET /api/tasks**
Get all tasks.

**Response (200):** Array of tasks

---

#### **GET /api/tasks/project/{projectId}**
Get all tasks for specific project.

**Response (200):** Array of tasks

---

#### **GET /api/tasks/user/{userId}**
Get all tasks assigned to specific user.

**Response (200):** Array of tasks

---

#### **GET /api/tasks/status/{status}**
Get tasks by status.

**Response (200):** Array of tasks

---

#### **GET /api/tasks/project/{projectId}/status/{status}**
Get project tasks filtered by status.

**Response (200):** Array of tasks

---

#### **GET /api/tasks/user/{userId}/status/{status}**
Get user tasks filtered by status.

**Response (200):** Array of tasks

---

#### **POST /api/tasks/search**
Search tasks by title.

**Request Body:**
```json
{
  "query": "Database"
}
```

**Response (200):** Array of tasks

---

#### **GET /api/tasks/overdue**
Get all overdue tasks.

**Response (200):** Array of tasks

---

#### **PUT /api/tasks/{id}**
Update entire task.

**Request Body:** Full task object

**Response (200):** Updated task

---

#### **PUT /api/tasks/{id}/status**
Update task status only.

**Request Body:**
```json
{
  "status": "IN_PROGRESS"
}
```

**Response (200):** Empty body with 200 status

---

#### **PUT /api/tasks/{id}/assign**
Assign task to different user.

**Request Body:**
```json
{
  "userId": 7
}
```

**Response (200):** Empty body with 200 status

---

#### **DELETE /api/tasks/{id}**
Delete task.

**Response (204):** No content

---

#### **GET /api/tasks/count**
Get total task count.

**Response (200):**
```json
87
```

---

### **Frontend UI Requirements for Tasks**

#### **Pages:**
1. **/tasks** - All tasks view (Kanban or list)
2. **/tasks/{id}** - Task detail modal/page
3. **/projects/{projectId}/tasks** - Project-specific tasks

#### **Components:**
- `TaskCard` - Compact task card with title, assignee, due date, status
- `TaskKanbanBoard` - Drag-and-drop board (TODO, IN_PROGRESS, IN_REVIEW, COMPLETED)
- `TaskList` - List view with sorting/filtering
- `TaskCreateModal` - Modal form for creating task
- `TaskEditModal` - Edit task details
- `TaskStatusDropdown` - Quick status change dropdown
- `TaskAssigneeSelect` - Dropdown to assign task to user
- `TaskFilters` - Filter by status, assignee, project, date
- `TaskDetailView` - Full task details with comments section

#### **State Management:**
```typescript
interface TaskState {
  tasks: Task[];
  currentTask: Task | null;
  loading: boolean;
  filters: TaskFilters;
  fetchTasks: () => Promise<void>;
  fetchTasksByProject: (projectId: number) => Promise<void>;
  fetchTasksByUser: (userId: number) => Promise<void>;
  createTask: (data: TaskCreateDto) => Promise<void>;
  updateTask: (id: number, data: Task) => Promise<void>;
  updateTaskStatus: (id: number, status: TaskStatus) => Promise<void>;
  assignTask: (id: number, userId: number) => Promise<void>;
  deleteTask: (id: number) => Promise<void>;
}
```

#### **Business Rules:**
- Only assigned user, ADMIN, or DEVELOPER can update task status
- Show overdue badge if past due date
- Kanban board must support drag-and-drop status changes
- Client users can only see tasks in their projects
- Send notification when task is assigned
- Automatically update project progress when task status changes

---

## 👥 4. TEAM COLLABORATION (ProjectUser)

### **API Endpoints**

#### **POST /api/project-users**
Create raw project-user relationship (use /add instead).

#### **POST /api/project-users/add**
Add user to project with specific role.

**Request Body:**
```json
{
  "projectId": 1,
  "userId": 3,
  "role": "DEVELOPER"
}
```

**Response (201):**
```json
{
  "id": 1,
  "projectId": 1,
  "userId": 3,
  "role": "DEVELOPER"
}
```

**Role Enum:** OWNER, ADMIN, DEVELOPER, VIEWER

---

#### **GET /api/project-users/{id}**
Get project-user relationship by ID.

**Response (200):** ProjectUser object with user and project details

---

#### **GET /api/project-users**
Get all project-user relationships.

**Response (200):** Array of ProjectUser objects

---

#### **GET /api/project-users/project/{projectId}**
Get all users in specific project.

**Response (200):** Array of ProjectUser objects

---

#### **GET /api/project-users/user/{userId}**
Get all projects for specific user.

**Response (200):** Array of ProjectUser objects

---

#### **GET /api/project-users/project/{projectId}/user/{userId}**
Check specific user's role in project.

**Response (200):** ProjectUser object

---

#### **GET /api/project-users/role/{role}**
Get all users with specific role across all projects.

**Response (200):** Array of ProjectUser objects

---

#### **GET /api/project-users/project/{projectId}/role/{role}**
Get users in project filtered by role.

**Response (200):** Array of ProjectUser objects

---

#### **POST /api/project-users/exists**
Check if user is in project.

**Request Body:**
```json
{
  "projectId": 1,
  "userId": 3
}
```

**Response (200):**
```json
true
```

---

#### **PUT /api/project-users/{id}**
Update project-user relationship.

**Request Body:** Full ProjectUser object

**Response (200):** Updated ProjectUser

---

#### **PUT /api/project-users/change-role**
Change user's role in project.

**Request Body:**
```json
{
  "projectId": 1,
  "userId": 3,
  "newRole": "ADMIN"
}
```

**Response (200):** Empty body with 200 status

---

#### **DELETE /api/project-users/{id}**
Remove user from project by relationship ID.

**Response (204):** No content

---

#### **DELETE /api/project-users/remove**
Remove user from project.

**Request Body:**
```json
{
  "projectId": 1,
  "userId": 3
}
```

**Response (204):** No content

---

#### **GET /api/project-users/count**
Get total project-user relationship count.

**Response (200):**
```json
125
```

---

### **Frontend UI Requirements for Team**

#### **Pages:**
1. **/projects/{id}/team** - Project team management page
2. **/projects/{id}/team/add** - Add team member modal

#### **Components:**
- `TeamMemberCard` - Card showing user avatar, name, role, actions
- `TeamMemberList` - List of team members with role badges
- `AddTeamMemberModal` - Search users + select role
- `TeamMemberRoleDropdown` - Change role dropdown
- `RemoveTeamMemberButton` - Remove member with confirmation
- `TeamRoleBadge` - Color-coded role indicator

#### **State Management:**
```typescript
interface ProjectUserState {
  teamMembers: ProjectUser[];
  loading: boolean;
  fetchTeamMembers: (projectId: number) => Promise<void>;
  addUserToProject: (data: ProjectUserAddDto) => Promise<void>;
  changeUserRole: (projectId: number, userId: number, role: ProjectAccessRole) => Promise<void>;
  removeUserFromProject: (projectId: number, userId: number) => Promise<void>;
  checkUserInProject: (projectId: number, userId: number) => Promise<boolean>;
}
```

#### **Business Rules:**
- Only OWNER or ADMIN can add/remove team members
- Cannot remove project OWNER
- At least one OWNER must remain
- Show user search when adding members
- Display role hierarchy: OWNER > ADMIN > DEVELOPER > VIEWER
- VIEWER can only view, no edit permissions

---

## 🔔 5. NOTIFICATION SYSTEM

### **API Endpoints**

#### **POST /api/notifications**
Create raw notification (use /create instead).

#### **POST /api/notifications/create**
Create new notification.

**Request Body:**
```json
{
  "message": "Your project has been approved",
  "type": "PROJECT_UPDATE",
  "userId": 5
}
```

**Response (201):**
```json
{
  "notificationId": 1,
  "message": "Your project has been approved",
  "type": "PROJECT_UPDATE",
  "userId": 5,
  "isRead": false,
  "createdAt": "2025-11-28T10:30:00"
}
```

**Type Enum:** PROJECT_UPDATE, TASK_ASSIGNED, DEADLINE_REMINDER, FEEDBACK_RECEIVED, DELIVERABLE_APPROVED, SYSTEM_ALERT

---

#### **GET /api/notifications/{id}**
Get notification by ID.

**Response (200):** Notification object

---

#### **GET /api/notifications**
Get all notifications (ADMIN only).

**Response (200):** Array of notifications

---

#### **GET /api/notifications/user/{userId}**
Get all notifications for user.

**Response (200):** Array of notifications

---

#### **GET /api/notifications/user/{userId}/ordered**
Get user notifications ordered by date (newest first).

**Response (200):** Array of notifications

---

#### **GET /api/notifications/user/{userId}/read/{isRead}**
Get user notifications filtered by read status.

**Response (200):** Array of notifications

---

#### **GET /api/notifications/type/{type}**
Get notifications by type.

**Response (200):** Array of notifications

---

#### **GET /api/notifications/user/{userId}/unread-count**
Get count of unread notifications for user.

**Response (200):**
```json
5
```

---

#### **PUT /api/notifications/{id}**
Update notification.

**Request Body:** Full notification object

**Response (200):** Updated notification

---

#### **PUT /api/notifications/{id}/mark-read**
Mark single notification as read.

**Response (200):** Empty body with 200 status

---

#### **PUT /api/notifications/user/{userId}/mark-all-read**
Mark all user notifications as read.

**Response (200):** Empty body with 200 status

---

#### **DELETE /api/notifications/{id}**
Delete notification.

**Response (204):** No content

---

#### **GET /api/notifications/count**
Get total notification count.

**Response (200):**
```json
342
```

---

### **Frontend UI Requirements for Notifications**

#### **Pages:**
1. **/notifications** - All notifications page

#### **Components:**
- `NotificationBell` - Bell icon in navbar with unread count badge
- `NotificationDropdown` - Dropdown showing recent 5 notifications
- `NotificationList` - Full list of notifications
- `NotificationItem` - Single notification with icon, message, timestamp
- `NotificationBadge` - Unread count indicator
- `NotificationTypeIcon` - Different icons for each notification type

#### **State Management:**
```typescript
interface NotificationState {
  notifications: Notification[];
  unreadCount: number;
  loading: boolean;
  fetchNotifications: (userId: number) => Promise<void>;
  fetchUnreadCount: (userId: number) => Promise<void>;
  markAsRead: (notificationId: number) => Promise<void>;
  markAllAsRead: (userId: number) => Promise<void>;
  deleteNotification: (id: number) => Promise<void>;
}
```

#### **Business Rules:**
- Poll for new notifications every 30 seconds
- Show toast notification for new real-time notifications
- Unread notifications have bold text + blue dot
- Click notification to mark as read and navigate to related item
- Group notifications by date (Today, Yesterday, This Week, Older)
- Auto-create notifications when:
  - Task assigned to user
  - Project status changes
  - Deliverable approved/rejected
  - Feedback received
  - Approaching deadline (1 day before)

---

## 💬 6. FEEDBACK SYSTEM

### **API Endpoints**

#### **POST /api/feedback**
Create raw feedback (use /add instead).

#### **POST /api/feedback/add**
Add feedback to deliverable.

**Request Body:**
```json
{
  "message": "Great work! Please update the color scheme as discussed.",
  "deliverableId": 2,
  "userId": 1
}
```

**Response (201):**
```json
{
  "feedbackId": 1,
  "message": "Great work! Please update the color scheme as discussed.",
  "deliverableId": 2,
  "userId": 1,
  "createdAt": "2025-11-28T10:30:00"
}
```

---

#### **GET /api/feedback/{id}**
Get feedback by ID.

**Response (200):** Feedback object

---

#### **GET /api/feedback**
Get all feedback.

**Response (200):** Array of feedback

---

#### **GET /api/feedback/deliverable/{deliverableId}**
Get all feedback for specific deliverable.

**Response (200):** Array of feedback

---

#### **GET /api/feedback/user/{userId}**
Get all feedback created by user.

**Response (200):** Array of feedback

---

#### **GET /api/feedback/project/{projectId}**
Get all feedback for project's deliverables.

**Response (200):** Array of feedback

---

#### **PUT /api/feedback/{id}**
Update feedback.

**Request Body:** Full feedback object

**Response (200):** Updated feedback

---

#### **DELETE /api/feedback/{id}**
Delete feedback.

**Response (204):** No content

---

#### **GET /api/feedback/count**
Get total feedback count.

**Response (200):**
```json
78
```

---

### **Frontend UI Requirements for Feedback**

#### **Components:**
- `FeedbackSection` - Section on deliverable detail page
- `FeedbackList` - List of feedback comments
- `FeedbackItem` - Single feedback with avatar, name, message, timestamp
- `FeedbackForm` - Text area to add new feedback
- `FeedbackThread` - Threaded conversation view

#### **State Management:**
```typescript
interface FeedbackState {
  feedbacks: Feedback[];
  loading: boolean;
  fetchFeedbackByDeliverable: (deliverableId: number) => Promise<void>;
  addFeedback: (data: FeedbackAddDto) => Promise<void>;
  updateFeedback: (id: number, message: string) => Promise<void>;
  deleteFeedback: (id: number) => Promise<void>;
}
```

#### **Business Rules:**
- Only project team members can add feedback
- Feedback cannot be edited after 5 minutes
- Show "edited" badge if feedback was modified
- Send notification to deliverable uploader when feedback added
- Support markdown formatting in feedback text
- Allow file attachments in feedback (optional)

---

## 📦 7. DELIVERABLE MANAGEMENT

### **API Endpoints**

#### **POST /api/deliverables**
Create raw deliverable (use /upload instead).

#### **POST /api/deliverables/upload**
Upload new deliverable.

**Request Body:**
```json
{
  "fileName": "homepage-mockup.pdf",
  "fileType": "PDF",
  "fileUrl": "https://storage.example.com/files/homepage-mockup.pdf",
  "projectId": 1
}
```

**Response (201):**
```json
{
  "deliverableId": 1,
  "fileName": "homepage-mockup.pdf",
  "fileType": "PDF",
  "fileUrl": "https://storage.example.com/files/homepage-mockup.pdf",
  "projectId": 1,
  "approved": false,
  "uploadedAt": "2025-11-28T10:30:00"
}
```

---

#### **GET /api/deliverables/{id}**
Get deliverable by ID.

**Response (200):** Deliverable object

---

#### **GET /api/deliverables**
Get all deliverables.

**Response (200):** Array of deliverables

---

#### **GET /api/deliverables/project/{projectId}**
Get all deliverables for project.

**Response (200):** Array of deliverables

---

#### **GET /api/deliverables/approved/{approved}**
Get deliverables by approval status.

**Response (200):** Array of deliverables

---

#### **GET /api/deliverables/project/{projectId}/approved/{approved}**
Get project deliverables filtered by approval status.

**Response (200):** Array of deliverables

---

#### **GET /api/deliverables/file-type/{fileType}**
Get deliverables by file type.

**Response (200):** Array of deliverables

---

#### **POST /api/deliverables/search**
Search deliverables by file name.

**Request Body:**
```json
{
  "query": "mockup"
}
```

**Response (200):** Array of deliverables

---

#### **PUT /api/deliverables/{id}**
Update deliverable.

**Request Body:** Full deliverable object

**Response (200):** Updated deliverable

---

#### **PUT /api/deliverables/{id}/approve**
Approve deliverable (ADMIN only).

**Response (200):** Empty body with 200 status

---

#### **DELETE /api/deliverables/{id}**
Delete deliverable.

**Response (204):** No content

---

#### **GET /api/deliverables/count**
Get total deliverable count.

**Response (200):**
```json
23
```

---

### **Frontend UI Requirements for Deliverables**

#### **Pages:**
1. **/deliverables** - All deliverables view
2. **/deliverables/{id}** - Deliverable detail with preview
3. **/projects/{id}/deliverables** - Project deliverables

#### **Components:**
- `DeliverableCard` - Card with file icon, name, upload date, approval status
- `DeliverableList` - Grid or list of deliverables
- `DeliverableUploadModal` - Drag-and-drop file upload
- `DeliverablePreview` - File preview (PDF, images, etc.)
- `DeliverableApprovalButton` - Approve/reject buttons (ADMIN)
- `DeliverableStatusBadge` - Pending/Approved badge
- `DeliverableFilters` - Filter by type, status, project

#### **File Upload Flow:**
1. User selects file using React Dropzone
2. Frontend uploads file to S3 (or backend handles it)
3. Get S3 URL back
4. Call `POST /api/deliverables/upload` with file metadata + URL
5. Show success notification

#### **State Management:**
```typescript
interface DeliverableState {
  deliverables: Deliverable[];
  currentDeliverable: Deliverable | null;
  loading: boolean;
  uploadFile: (file: File, projectId: number) => Promise<void>;
  fetchDeliverables: () => Promise<void>;
  fetchDeliverablesByProject: (projectId: number) => Promise<void>;
  approveDeliverable: (id: number) => Promise<void>;
  deleteDeliverable: (id: number) => Promise<void>;
  searchDeliverables: (query: string) => Promise<void>;
}
```

#### **Business Rules:**
- Max file size: 50MB
- Supported types: PDF, PNG, JPG, DOCX, ZIP, MP4
- Show upload progress bar
- Only ADMIN can approve deliverables
- Send notification when deliverable approved
- Generate thumbnail for images
- Display file size in human-readable format

---

## 🎨 UI/UX Design Guidelines

### **Layout Structure**
```
┌─────────────────────────────────────────┐
│ Header/Navbar                           │
│ - Logo, Navigation, Search, Profile     │
├──────────┬──────────────────────────────┤
│          │                              │
│ Sidebar  │   Main Content Area          │
│          │                              │
│ - Home   │   - Page Content             │
│ - Projects│  - Cards/Tables/Forms      │
│ - Tasks  │                              │
│ - Team   │                              │
│ - Delivs │                              │
│ - Notifs │                              │
│          │                              │
└──────────┴──────────────────────────────┘
```

### **Color Scheme**
- **Primary**: Blue (#3B82F6) - Actions, links
- **Success**: Green (#10B981) - Completed, approved
- **Warning**: Yellow (#F59E0B) - Pending, overdue
- **Danger**: Red (#EF4444) - Blocked, rejected
- **Neutral**: Gray (#6B7280) - Text, borders

### **Status Color Mapping**
**Projects:**
- NOT_STARTED: Gray
- IN_PROGRESS: Blue
- ON_HOLD: Yellow
- COMPLETED: Green
- CANCELLED: Red

**Tasks:**
- TODO: Gray
- IN_PROGRESS: Blue
- IN_REVIEW: Purple
- COMPLETED: Green
- BLOCKED: Red

### **Typography**
- Headings: Inter or Poppins (Bold)
- Body: Inter or Roboto (Regular)
- Monospace: Fira Code (for code snippets)

---

## 🔒 Authentication & Authorization

### **Protected Routes**
All routes except `/auth/login` and `/auth/register` require authentication.

### **Route Guards**
```typescript
// Redirect to login if not authenticated
if (!isAuthenticated) {
  router.push('/auth/login');
}

// Check role-based access
const canAccess = (requiredRole: UserRole) => {
  const roleHierarchy = {
    ADMIN: 3,
    DEVELOPER: 2,
    CLIENT: 1
  };
  return roleHierarchy[user.role] >= roleHierarchy[requiredRole];
};
```

### **Permission Matrix**
| Feature | CLIENT | DEVELOPER | ADMIN |
|---------|--------|-----------|-------|
| View own projects | ✅ | ✅ | ✅ |
| View all projects | ❌ | ✅ | ✅ |
| Create project | ❌ | ❌ | ✅ |
| Edit project | ❌ | ✅ | ✅ |
| Delete project | ❌ | ❌ | ✅ |
| View tasks | ✅ | ✅ | ✅ |
| Create task | ❌ | ✅ | ✅ |
| Update task status | ✅ (own) | ✅ | ✅ |
| Assign task | ❌ | ✅ | ✅ |
| Upload deliverable | ✅ | ✅ | ✅ |
| Approve deliverable | ❌ | ❌ | ✅ |
| Add feedback | ✅ | ✅ | ✅ |
| Manage users | ❌ | ❌ | ✅ |
| Add team members | ❌ | ✅ (ADMIN role) | ✅ |

---

## 📱 Responsive Design Requirements

### **Breakpoints**
- Mobile: < 768px
- Tablet: 768px - 1024px
- Desktop: > 1024px

### **Mobile Adaptations**
- Hamburger menu for sidebar navigation
- Stacked cards instead of grid
- Bottom tab navigation
- Collapsible filters
- Touch-friendly buttons (min 44x44px)

---

## 🔄 State Management Architecture

### **Zustand Store Structure**
```typescript
// stores/authStore.ts
// stores/projectStore.ts
// stores/taskStore.ts
// stores/deliverableStore.ts
// stores/notificationStore.ts
// stores/feedbackStore.ts
// stores/projectUserStore.ts
```

### **Global State vs Component State**
- **Global:** User auth, notifications, current project/task
- **Local:** Form inputs, modal open/close, local filters

---

## 🌐 API Integration Best Practices

### **Axios Configuration**
```typescript
// lib/axios.ts
import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json'
  }
});

// Request interceptor - add auth token
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('authToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Response interceptor - handle errors
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Redirect to login
      window.location.href = '/auth/login';
    }
    return Promise.reject(error);
  }
);

export default apiClient;
```

### **Error Handling**
```typescript
try {
  await apiClient.post('/users/login', credentials);
} catch (error) {
  if (axios.isAxiosError(error)) {
    if (error.response?.status === 400) {
      // Validation error
      toast.error(error.response.data.message);
    } else if (error.response?.status === 404) {
      // Not found
      toast.error('User not found');
    } else {
      // Generic error
      toast.error('Something went wrong');
    }
  }
}
```

---

## 📋 Form Validation Schemas (Zod)

### **Login Form**
```typescript
const loginSchema = z.object({
  email: z.string().email('Invalid email format'),
  password: z.string().min(8, 'Password must be at least 8 characters')
});
```

### **Register Form**
```typescript
const registerSchema = z.object({
  name: z.string().min(2, 'Name must be at least 2 characters'),
  email: z.string().email('Invalid email format'),
  password: z.string()
    .min(8, 'Password must be at least 8 characters')
    .regex(/[A-Z]/, 'Must contain uppercase letter')
    .regex(/[a-z]/, 'Must contain lowercase letter')
    .regex(/[0-9]/, 'Must contain number'),
  confirmPassword: z.string(),
  role: z.enum(['CLIENT', 'DEVELOPER', 'ADMIN'])
}).refine((data) => data.password === data.confirmPassword, {
  message: "Passwords don't match",
  path: ['confirmPassword']
});
```

### **Project Create Form**
```typescript
const projectCreateSchema = z.object({
  clientId: z.number().positive(),
  title: z.string().min(3, 'Title must be at least 3 characters'),
  description: z.string().min(10, 'Description must be at least 10 characters'),
  startDate: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Invalid date format'),
  dueDate: z.string().regex(/^\d{4}-\d{2}-\d{2}$/, 'Invalid date format')
}).refine((data) => new Date(data.dueDate) > new Date(data.startDate), {
  message: 'Due date must be after start date',
  path: ['dueDate']
});
```

---

## 🎯 Navigation Structure

### **Main Navigation (Sidebar)**
```
Home / Dashboard
├── Projects
│   ├── All Projects
│   ├── My Projects
│   ├── Overdue Projects
│   └── Create Project (ADMIN)
├── Tasks
│   ├── All Tasks
│   ├── My Tasks
│   ├── Kanban Board
│   └── Overdue Tasks
├── Deliverables
│   ├── All Deliverables
│   ├── Pending Approval
│   └── Approved
├── Team
│   └── All Members (ADMIN)
├── Notifications
└── Profile
    ├── View Profile
    ├── Edit Profile
    └── Change Password
```

### **Breadcrumb Examples**
- Home > Projects > E-Commerce Website > Team
- Home > Tasks > Design Database Schema
- Home > Deliverables > homepage-mockup.pdf

---

## 📊 Dashboard Overview Page

### **Widgets/Cards**
1. **Stats Row:**
   - Total Projects
   - Active Tasks
   - Pending Deliverables
   - Unread Notifications

2. **Recent Activity Feed:**
   - Task assignments
   - Deliverable uploads
   - Project status changes
   - Feedback received

3. **Upcoming Deadlines:**
   - Tasks due in next 7 days
   - Projects due in next 30 days

4. **Quick Actions:**
   - Create Project (ADMIN)
   - Create Task
   - Upload Deliverable

5. **Project Progress Charts:**
   - Pie chart: Projects by status
   - Bar chart: Tasks by status
   - Line chart: Deliverables over time

---

## 🔍 Search Functionality

### **Global Search Bar**
- Search across projects, tasks, deliverables
- Real-time suggestions dropdown
- Keyboard shortcut: Ctrl+K / Cmd+K
- Recent searches history

### **Search Results Page**
- Tabbed interface: Projects | Tasks | Deliverables
- Highlight matching text
- Filter by date, type, status

---

## 🎨 Component Library (shadcn/ui)

### **Required Components**
- Button, Input, Textarea
- Card, Badge, Avatar
- Dialog/Modal, Sheet (sidebar)
- Dropdown Menu, Select
- Table, Tabs
- Form, Label
- Toast/Sonner
- Calendar, Date Picker
- Progress Bar
- Skeleton Loader
- Alert, Alert Dialog
- Popover, Tooltip
- Command (search)
- Switch, Checkbox, Radio Group

---

## 🚀 Performance Optimization

### **Code Splitting**
- Lazy load routes
- Dynamic imports for modals
- Separate bundles for admin features

### **Data Fetching**
- Use React Query or SWR for caching
- Implement pagination (20 items per page)
- Infinite scroll for feeds
- Debounce search inputs (300ms)

### **Image Optimization**
- Use Next.js Image component
- Lazy load images
- Generate thumbnails for deliverables

---

## 🧪 Testing Recommendations

### **Unit Tests**
- Test utility functions
- Test Zod schemas
- Test state management actions

### **Integration Tests**
- Test API integration layer
- Test form submissions
- Test authentication flow

### **E2E Tests (Playwright)**
- Login → Create Project → Add Task → Upload Deliverable
- Complete task workflow
- Team member invitation flow

---

## 📦 Deployment

### **Environment Variables**
```
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080/api
NEXT_PUBLIC_S3_BUCKET_URL=https://your-bucket.s3.amazonaws.com
```

### **Build Command**
```bash
npm run build
```

### **Hosting Recommendations**
- Vercel (Next.js optimized)
- Netlify
- AWS Amplify

---

## 🎯 Final Implementation Checklist

### **Phase 1: Foundation**
- [ ] Setup Next.js project with TypeScript
- [ ] Install dependencies (Tailwind, shadcn/ui, Zustand, React Hook Form, Zod, Axios)
- [ ] Configure API client with interceptors
- [ ] Setup authentication store
- [ ] Create protected route HOC
- [ ] Build login/register pages

### **Phase 2: Core Features**
- [ ] Build dashboard layout with sidebar
- [ ] Implement project management (CRUD)
- [ ] Implement task management (CRUD + Kanban)
- [ ] Build team management
- [ ] Implement notifications system

### **Phase 3: Advanced Features**
- [ ] Build deliverable upload with file handling
- [ ] Implement feedback system
- [ ] Add search functionality
- [ ] Create dashboard with stats/charts
- [ ] Implement role-based permissions

### **Phase 4: Polish**
- [ ] Add loading states and skeletons
- [ ] Error handling and toast notifications
- [ ] Responsive design for mobile
- [ ] Form validation on all forms
- [ ] Accessibility (ARIA labels, keyboard navigation)
- [ ] Performance optimization (code splitting, lazy loading)

---

## 📝 TypeScript Interfaces

### **Core Types**
```typescript
enum UserRole {
  CLIENT = 'CLIENT',
  DEVELOPER = 'DEVELOPER',
  ADMIN = 'ADMIN'
}

interface User {
  userId: number;
  name: string;
  email: string;
  role: UserRole;
  companyName?: string;
  phone?: string;
  lastLogin?: string;
  createdAt: string;
}

enum ProjectStatus {
  NOT_STARTED = 'NOT_STARTED',
  IN_PROGRESS = 'IN_PROGRESS',
  ON_HOLD = 'ON_HOLD',
  COMPLETED = 'COMPLETED',
  CANCELLED = 'CANCELLED'
}

interface Project {
  projectId: number;
  clientId: number;
  title: string;
  description: string;
  startDate: string;
  dueDate: string;
  status: ProjectStatus;
  progress: number;
}

enum TaskStatus {
  TODO = 'TODO',
  IN_PROGRESS = 'IN_PROGRESS',
  IN_REVIEW = 'IN_REVIEW',
  COMPLETED = 'COMPLETED',
  BLOCKED = 'BLOCKED'
}

interface Task {
  taskId: number;
  title: string;
  description: string;
  projectId: number;
  assignedToId: number;
  dueDate: string;
  status: TaskStatus;
}

enum ProjectAccessRole {
  OWNER = 'OWNER',
  ADMIN = 'ADMIN',
  DEVELOPER = 'DEVELOPER',
  VIEWER = 'VIEWER'
}

interface ProjectUser {
  id: number;
  projectId: number;
  userId: number;
  role: ProjectAccessRole;
  user?: User;
  project?: Project;
}

enum NotificationType {
  PROJECT_UPDATE = 'PROJECT_UPDATE',
  TASK_ASSIGNED = 'TASK_ASSIGNED',
  DEADLINE_REMINDER = 'DEADLINE_REMINDER',
  FEEDBACK_RECEIVED = 'FEEDBACK_RECEIVED',
  DELIVERABLE_APPROVED = 'DELIVERABLE_APPROVED',
  SYSTEM_ALERT = 'SYSTEM_ALERT'
}

interface Notification {
  notificationId: number;
  message: string;
  type: NotificationType;
  userId: number;
  isRead: boolean;
  createdAt: string;
}

interface Deliverable {
  deliverableId: number;
  fileName: string;
  fileType: string;
  fileUrl: string;
  projectId: number;
  approved: boolean;
  uploadedAt: string;
}

interface Feedback {
  feedbackId: number;
  message: string;
  deliverableId: number;
  userId: number;
  createdAt: string;
  user?: User;
}
```

---

## 🎉 Success Criteria

Your frontend is complete when:
- ✅ All 7 modules are fully functional
- ✅ All API endpoints are integrated
- ✅ Authentication works with role-based access
- ✅ Forms have proper validation
- ✅ File upload works for deliverables
- ✅ Real-time notifications update
- ✅ Mobile responsive on all pages
- ✅ Error handling shows user-friendly messages
- ✅ Loading states prevent blank screens
- ✅ User can complete full workflow: Login → Create Project → Add Task → Upload Deliverable → Provide Feedback

---

**This prompt contains everything needed to build a production-ready frontend that perfectly integrates with your Client Portal backend. Use it with any AI code generator (Lovable, v0.dev, Bolt.new, etc.) to generate a complete React/Next.js application.** 🚀
