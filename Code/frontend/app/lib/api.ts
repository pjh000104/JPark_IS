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

export async function getCuisineByName(cuisineName: string) {
  console.log("CUisineName:", cuisineName);
  try {
    const res = await fetch(
      `${API_BASE_URL}/api/cuisines/${encodeURIComponent(cuisineName)}`,
      {
        credentials: "include",
      }
    );

    if (!res.ok) {
      throw new Error(`Failed to fetch cuisine: ${cuisineName}`);
    }

    const data = await res.json();
    return data; // This will be the CuisineResponse object from the backend
  } catch (error) {
    console.error("Error fetching cuisine:", error);
    return null; // or undefined, depending on your preference
  }
}

export async function getReviewsByCuisineId(cuisineId: number) {
  try {
    const res = await fetch(
      `${API_BASE_URL}/api/reviews/cuisine/${cuisineId}`,
      {
        credentials: "include",
      }
    );

    if (!res.ok) {
      throw new Error("Failed to fetch reviews");
    }

    return await res.json();
  } catch (error) {
    console.error("Error fetching reviews:", error);
    return [];
  }
}

export async function createReview(data: {
  cuisineId: number;
  rating: number;
  comment: string;
}) {
  const response = await fetch(
    `${API_BASE_URL}/api/reviews`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    body: JSON.stringify(data),
    });

  if (!response.ok) {
    throw new Error("Failed to create review");
  }

  return response.json();
}