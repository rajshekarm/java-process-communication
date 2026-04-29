# Speed Test

This project is a learning-first experiment to compare process-to-process communication methods and measure throughput.

We are starting small on purpose:

- one parent process
- one child process
- one simple data exchange
- one benchmark harness that can swap communication methods later

## What We Are Building

We will build a small Java benchmark that starts with the simplest communication method first and then moves toward faster ones.

The first version only needs to prove this flow:

1. start one Java process from another
2. exchange one small payload
3. verify the payload arrived correctly
4. print success

Once that works, we can add:

- timing
- larger payloads
- repeated iterations
- multiple communication implementations
- result comparison from slowest to fastest

## How We Will Keep It Simple

We do not need separate parent and child application classes at the start.

A single `Main` class can accept a mode such as:

- `parent`
- `child`

That keeps the first prototype easy to understand and easy to change.

## Decision Log

- [Basic two-process communication](docs/decisions/0001-basic-process-communication.md)
- [Decision note template](docs/decisions/template.md)

Important architecture choices should live in numbered files under `docs/decisions/`.
