FROM public.ecr.aws/lambda/python:3.8

COPY requirements.txt ${LAMBDA_TASK_ROOT}

RUN pip install -r requirements.txt

COPY app.py config.py errors.py lambda_handler.py model.py ${LAMBDA_TASK_ROOT}

CMD ["lambda_handler.lambda_handler"]