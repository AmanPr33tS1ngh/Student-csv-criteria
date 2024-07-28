# Student API

This API allows for managing student information, including uploading CSV files containing student data and retrieving student details by roll number.

## Endpoints

### 1. Upload CSV

- **URL**: `/student/upload_csv`
- **Method**: `POST`
- **Consumes**: `multipart/form-data`
- **Description**: This endpoint allows for uploading a CSV file containing student data. The CSV file should follow the required format to ensure successful data import.

  **Request**:
`POST /student/upload_csv`
`Content-Type: multipart/form-data`

**Form Data**:
- `file`: The CSV file to be uploaded.

**Response**:
- `200 OK`: Successfully uploaded and processed CSV.
  - Content: The updated CSV file.
- `400 Bad Request`: File is empty or invalid.
- `500 Internal Server Error`: Internal server error.

### 2. Get Student by Roll Number

- **URL**: `/student/{rollNumber}`
- **Method**: `GET`
- **Description**: This endpoint retrieves the details of a student by their roll number.

**Request**:
`GET /student/{rollNumber}`


**Path Parameter**:
- `rollNumber`: The roll number of the student whose details are to be retrieved.

**Response**:
- `200 OK`: Got student.
- `404 Not Found`: Student not found.

### 3. Criteria

- **URL**: `/criteria`
- **Methods**: `GET`, `POST`
- **Description**: This endpoint handles operations related to criteria.

**GET Request**:
`GET /criteria`


**Response**:
- `200 OK`: Returns the criteria details.

**POST Request**:
POST /criteria


**Request Body**:
- `
{
  "maths": 30,
  "science": 40,
  "english": 50,
  "computer": 60
}
`

**Response**:
- `200 OK`: Successfully created or updated criteria.
- `400 Bad Request`: Error in the request body or processing.
