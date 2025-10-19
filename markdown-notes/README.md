# Weather Api Wrapper

[Project URL](https://roadmap.sh/projects/markdown-note-taking-app)

## Getting the source code

1. Clone the GitHub repository:
   ```shell
   git clone https://github.com/himanshu-pareek/roadmapsh-backend-projects.git
    ```
   
2. Go to `markdown-notes` directory:
    ```shell
    cd roadmap-backend-projects
    cd markdown-notes
    ```

## Running the application

```shell
./gradlew bootRun
```

## Using the application

### Uploading a markdown note file

Look at the below sample `curl` command to upload a markdown file located at `/a/b/note.md` with the title `My First Note`:

```shell
curl -X POST \
  -F "title=My First Note" \
  -F "file=@/a/b/note.md" \
  http://localhost:8080/api/notes
```

Sample response:

```json
{
  "id":"4172762d-ed89-4a7f-876d-5c2a784978e7",
  "title":"My First Note",
  "objectId":"faf0453d-381b-453e-a875-a67036b36b0b"
}
```

### Retrieving list of all uploaded notes

Look at the below sample `curl` command to get the list of all uploaded notes:

```shell
curl -X GET http://localhost:8080/api/notes
```

Sample response:

```json
[
  {
    "id":"4172762d-ed89-4a7f-876d-5c2a784978e7",
    "title":"My First Note",
    "objectId":"faf0453d-381b-453e-a875-a67036b36b0b"
  },
  {
    "id":"b1c4e8f2-3c4d-4e5f-9a6b-7c8d9e0f1a2b",
    "title":"My Second Note",
    "objectId":"c1d2e3f4-5678-90ab-cdef-1234567890ab"
  }
]
```

### Rendering a specific note in HTML by ID

Open the following URL in your web browser to render the note with ID `4172762d-ed89-4a7f-876d-5c2a784978e7`:

```text
http://localhost:8080/api/notes/4172762d-ed89-4a7f-876d-5c2a784978e7/render
```

This will display the rendered HTML content of the specified markdown note.
