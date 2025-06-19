<script setup lang="ts">
import Navbar from '~/app/components/layout/Navbar.vue'
import SpotlightCard from '~/app/components/SpotlightCard.vue'
import ArticleCard from '~/app/components/ArticleCard.vue'
import SpotlightButton from '~/app/components/SpotlightButton.vue'
import Divider from '~/app/components/Divider.vue'

const playerName = 'Omer Ozturk'
const stats = [
  { label: 'Total Goals', value: 18, icon: 'mdi:soccer' },
  { label: 'Total Assists', value: 12, icon: 'mdi:handshake' },
  { label: 'Avg. Speed (km/h)', value: 28.4, icon: 'mdi:speedometer' },
]
const recentVideos = [
  {
    title: 'Vs XYZ High School',
    image: '/public/articles/5-raycast-snippets.jpg',
    path: '/videos/1',
  },
  {
    title: 'Tournament Final',
    image: '/public/articles/launch-your-porfolio.jpg',
    path: '/videos/2',
  },
  {
    title: 'Season Highlights',
    image: '/public/articles/simplicity-is-the-ultimate-sophistication.jpg',
    path: '/videos/3',
  },
]
</script>

<template>
  <div class="min-h-screen bg-zinc-950 text-white">
    <Navbar />
    <main class="mx-auto max-w-5xl px-4 py-8">
      <h1 class="text-3xl font-bold mb-8">Welcome back, {{ playerName }}!</h1>
      <!-- Section 1: Summary Cards -->
      <div class="grid grid-cols-1 sm:grid-cols-3 gap-6 mb-10">
        <SpotlightCard v-for="stat in stats" :key="stat.label" white class="flex flex-col items-center p-6">
          <span class="text-4xl mb-2">
            <UIcon :name="stat.icon" />
          </span>
          <span class="text-2xl font-semibold">{{ stat.value }}</span>
          <span class="text-sm text-zinc-400 mt-1">{{ stat.label }}</span>
        </SpotlightCard>
      </div>
      <!-- Section 2: Recent Video Uploads -->
      <div class="mb-10">
        <h2 class="text-xl font-semibold mb-4">Recent Video Uploads</h2>
        <div class="grid grid-cols-1 sm:grid-cols-3 gap-6">
          <ArticleCard v-for="video in recentVideos" :key="video.title" :title="video.title" :date="'May 2024'" :image="video.image" :path="video.path" />
        </div>
      </div>
      <Divider class="my-8" />
      <!-- Section 3: Update Profile Button -->
      <div class="flex justify-center">
        <SpotlightButton as="a" :href="'/profile/edit'" class="px-8 py-3 text-lg font-semibold">
          Update Profile
        </SpotlightButton>
      </div>
    </main>
  </div>
</template>
