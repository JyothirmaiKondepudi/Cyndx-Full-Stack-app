FROM python:3.12.11-slim

COPY requirements.txt ${LAMBDA_TASK_ROOT}

RUN pip install -r requirements.txt

COPY . .

EXPOSE 5000

CMD ["lambda_handler.lambda_handler"]