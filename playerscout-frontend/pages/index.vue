<template>
  <div class="flex flex-col items-center justify-center min-h-screen">
    <h1 class="text-4xl font-bold mb-4">Welcome to PlayerScout</h1>
    <p class="mb-8">Showcase your soccer skills and stats to scouts and coaches!</p>
    <NuxtLink to="/register" class="btn btn-primary">Get Started</NuxtLink>
  </div>
</template>

<script setup>
// ...
</script>

<style scoped>
.btn {
  @apply px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700;
}
</style>
