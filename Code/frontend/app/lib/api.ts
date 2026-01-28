const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

export async function getRegions() {
  const res = await fetch(`${API_BASE_URL}/api/locations/all`, {
    credentials: "include",
  });

  if (!res.ok) {
    throw new Error("Failed to fetch cuisines");
  }
  return res.json();
}

export async function getCuisinesByRegion(region: string) {
  try {
    const res = await fetch(`${API_BASE_URL}/api/cuisines/byRegion/${region}`, {
      credentials: "include",
    });

    if (!res.ok) {
      throw new Error(`Failed to fetch cuisines for region: ${region}`);
    }

    const data = await res.json();
    return data;
  } catch (error) {
    console.error("Error fetching cuisines:", error);
    return []; 
  }
}