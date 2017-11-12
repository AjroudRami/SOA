FROM endeveit/docker-jq

WORKDIR /flow

COPY run.sh .
RUN chmod +x run.sh

COPY list scenarios

ENTRYPOINT ./run.sh

