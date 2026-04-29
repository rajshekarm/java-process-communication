# Basic Two-Process Communication

- Status: accepted
- Date: 2026-04-29

## Context

We want the first working version of the project to prove that two Java processes can communicate.

At this stage we are not trying to solve every future transport problem.
We are trying to build the smallest working baseline first so we can learn from it.

## Decision

Use one Java class with two modes:

- `parent`
- `child`

The parent process launches the child process and communicates with it through standard input and standard output.

The parent writes a message, the child reads it, and the child writes back a response.

## Why We Chose This

This is the simplest reliable starting point.

It gives us:

- two separate operating system processes
- a real message exchange
- a simple way to verify correctness
- no extra abstraction before we need it

The design keeps the first implementation easy to read and easy to debug.

## Pattern Used

This implementation is not a full transport abstraction.
It is a direct process communication proof.

The main idea is:

- one executable
- one parent mode
- one child mode
- stdin/stdout for message passing

That makes it a practical baseline before introducing a more flexible channel interface.

## What This Enables

This baseline lets us later measure and compare:

- larger payloads
- repeated messages
- timing
- alternative communication mechanisms

Once the basic parent-child flow works, we can replace stdin/stdout with a more general pluggable communication design if needed.

## Alternatives Considered

### 1. Start with a channel abstraction immediately

This would be more flexible, but it adds complexity before we have proven the simplest case.

### 2. Use separate parent and child programs

This would work, but it adds more files and setup for the first experiment.

### 3. Use one Java program with parent and child modes

This keeps the first version small while still using two real processes.

## Consequences

### Positive

- Very easy to understand
- Easy to run
- Easy to verify
- Gives us a working baseline quickly

### Tradeoffs

- stdin/stdout is not the final long-term design if we want many transport options
- the implementation is intentionally simple rather than abstract

For the first step, that tradeoff is worth it.

## Follow-up

The next implementation steps are:

- add timing
- send a larger payload
- repeat the exchange many times
- decide whether to keep building on the direct parent-child setup or reintroduce a transport abstraction

