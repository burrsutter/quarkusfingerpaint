FROM registry.access.redhat.com/ubi7-dev-preview/ubi-minimal
WORKDIR /work/
COPY target/quarkusfingerpaint-1.0.0-runner /work/quarkusfingerpaint-1.0.0-runner
RUN chmod 775 /work
EXPOSE 8080
ENTRYPOINT [ "./quarkusfingerpaint-1.0.0-runner",  "-Xmx8m", "-Xmn8m", "-Xms8m"]